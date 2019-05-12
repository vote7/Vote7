package agh.vote7.data

import retrofit2.Retrofit
import retrofit2.create

object RestApiProvider {
    val restApi: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://materia.serveo.net/")
            .build()

        retrofit.create<RestApi>()
    }
}
