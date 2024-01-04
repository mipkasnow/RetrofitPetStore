package test;

import jupiter.AddPet;
import jupiter.DeletePetAfterTest;
import model.Message;
import model.Pet;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static model.Status.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

public class PetSore extends BaseRestTest {

    @AddPet
    @DeletePetAfterTest
    @Test
    void shouldGetCreatedPet(Pet pet) throws IOException {
        Pet getPet = petController.getPetById(API_V2, pet.id().toString()).execute().body();
        assertThat(getPet).isEqualTo(pet);
    }

    @AddPet
    @DeletePetAfterTest
    @Test
    void shouldDeletePet(Pet pet) throws IOException {
        Message response = petController.deletePetById(API_V2, pet.id().toString()).execute().body();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.message()).isEqualTo(pet.id().toString());
        int code = petController.deletePetById(API_V2, pet.id().toString()).execute().code();
        assertThat(code).isEqualTo(404);
    }

    @AddPet
    @DeletePetAfterTest
    @Test
    void shouldFindPet(Pet pet) throws IOException {
        List<Pet> pets = petController.getPetByStatus(API_V2, AVAILABLE.status()).execute().body();
        pets.forEach(p -> assertThat(p.status()).isEqualTo(AVAILABLE.status()));

        Pet petFromList = pets.stream()
                .filter(p -> p.equals(pet))
                .toList().get(0);

        assertThat(pet).isEqualTo(petFromList);
    }

    @Test
    public void shouldUploadFile() throws IOException {
        List<Pet> pets = petController.getPetByStatus(API_V2, AVAILABLE.status()).execute().body();
        Pet pet = pets.stream()
                .findAny()
                .get();

        File image = new File("src/test/resources/files/image.jpg");
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), image);

        Message response = petController.uploadImageToPet(API_V2, pet.id().toString(), reqFile, "Hello").execute().body();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.message()).isEqualTo("additionalMetadata: \"Hello\"\nFile uploaded to ./image.jpg, 142102 bytes");
    }

}
