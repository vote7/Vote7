package agh.vote7.login.data.register

import agh.vote7.login.data.Result
import agh.vote7.login.data.Result.Success

class RegisterRepository(val dataSource: RegisterDataSource) {

    fun register(name: String, surname: String, email: String, password: String) : Result<Any> {
        return dataSource.register(name, surname, email, password)
    }
}