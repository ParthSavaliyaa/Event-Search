package algonquin.cst2335.finalproject.Network;



import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    /**
     * @param title title(Movie name)
     * @return Movie Name from the API
     */
    @GET("/?apikey=6c9862c2")
    Call<Model> listSearchedMovies(@Query("t") String title);

    /**
     * this is the case call class for API calling.
     * Used retrofit library to get the data from the API
     */
    class Factory {
        private static API service;

        public static API getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://www.omdbapi.com/")
                        .build();

                service = retrofit.create(API.class);
            }
            return service;
        }
    }
}
