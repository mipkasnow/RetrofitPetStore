package jupiter;

import com.github.javafaker.Faker;
import controller.PetService;
import model.IdName;
import model.Pet;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.Status.AVAILABLE;

public class AddPetExtension implements BeforeEachCallback, ParameterResolver {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().build();
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .client(HTTP_CLIENT)
            .baseUrl("https://petstore.swagger.io/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final PetService petController = RETROFIT.create(PetService.class);
    private static final String API_V2 = "v2";


    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(AddPetExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<AddPet> createPetAnnotation = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                AddPet.class
        );

        if (createPetAnnotation.isPresent()) {
            Pet pet = createRndPet();
            petController.addPet(API_V2, pet).execute();
            extensionContext.getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), pet);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(Pet.class);
    }

    @Override
    public Pet resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Pet.class);
    }

    private Pet createRndPet() {
        Faker faker = new Faker();
        IdName category = new IdName(
                (long) faker.random().nextInt(0, 30),
                faker.animal().name()
        );
        List<String> urls = new ArrayList<>();
        urls.add("https://dogphoto.com");
        urls.add("https://catphoto.com");

        List<IdName> tags = new ArrayList<>();
        tags.add(new IdName(1L, "fuzzy"));
        tags.add(new IdName(2L, "big"));

        return new Pet(
                (long) faker.random().nextInt(0, 30),
                category,
                faker.dog().name(),
                urls,
                tags,
                AVAILABLE.status()
        );
    }
}
