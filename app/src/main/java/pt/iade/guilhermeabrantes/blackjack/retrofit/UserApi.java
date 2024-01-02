package pt.iade.guilhermeabrantes.blackjack.retrofit;

import java.util.List;

import pt.iade.guilhermeabrantes.blackjack.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/user/get-all")
    Call<List<User>> getAllUsers();

    @POST("/user/save")
    Call<User> save(@Body User user);

    @DELETE("/user/delete")
    Call<User> delete(@Body User user);

    }
