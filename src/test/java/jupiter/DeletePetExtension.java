package jupiter;


import controller.PetService;
import model.Pet;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Optional;

public class DeletePetExtension implements AfterTestExecutionCallback {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().build();
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .client(HTTP_CLIENT)
            .baseUrl("https://petstore.swagger.io/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final PetService petController = RETROFIT.create(PetService.class);
    private static final String API_V2 = "v2";

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Optional<DeletePetAfterTest> deletePetAnnotation = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                DeletePetAfterTest.class
        );

        if (deletePetAnnotation.isPresent()) {
            Pet pet = (Pet) extensionContext.getStore(AddPetExtension.NAMESPACE)
                    .get(extensionContext.getUniqueId());
            petController.deletePetById(API_V2, pet.id().toString()).execute();
        }
    }
}
