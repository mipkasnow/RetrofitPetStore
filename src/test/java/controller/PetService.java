package controller;

import model.Message;
import model.Pet;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PetService {

    @POST("{api}/pet")
    Call<Pet> addPet(@Path("api") String api, @Body Pet pet);

    @GET("{api}/pet/{id}")
    Call<Pet> getPetById(@Path("api") String api, @Path("id") String id);

    @DELETE("{api}/pet/{id}")
    Call<Message> deletePetById(@Path("api") String api, @Path("id") String id);

    @GET("{api}/pet/findByStatus")
    Call<List<Pet>> getPetByStatus(@Path("api") String api, @Query("status") String status);

    @Multipart
    @POST("{api}/pet/{petId}/uploadImage")
    Call<Message> uploadImageToPet(@Path("api") String api,
                                   @Path("petId") String petId,
                                   @Part("file\"; filename=\"image.jpg") RequestBody file,
                                   @Part("additionalMetadata") String additionalMetadata);

}
