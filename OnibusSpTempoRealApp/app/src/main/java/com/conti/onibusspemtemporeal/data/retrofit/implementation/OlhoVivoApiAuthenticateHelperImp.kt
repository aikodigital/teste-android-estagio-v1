package com.conti.onibusspemtemporeal.data.retrofit.implementation

import com.conti.onibusspemtemporeal.data.retrofit.interfaces.OlhoVivoApiAuthenticateInterface
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiAuthenticateServiceInterface
import javax.inject.Inject


class OlhoVivoApiAuthenticateHelperImp @Inject constructor(
    private val olhoVivoApiAuthenticateServiceInterface: OlhoVivoApiAuthenticateServiceInterface
) : OlhoVivoApiAuthenticateInterface {

    override suspend fun postAuthenticate() = olhoVivoApiAuthenticateServiceInterface.postAuthenticate()

}