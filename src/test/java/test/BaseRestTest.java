package test;

import controller.PetService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class BaseRestTest {

    protected static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().build();
    protected static final Retrofit RETROFIT = new Retrofit.Builder()
            .client(HTTP_CLIENT)
            .baseUrl("https://petstore.swagger.io/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    protected final PetService petController = RETROFIT.create(PetService.class);
    protected static final String API_V2 = "v2";
}
