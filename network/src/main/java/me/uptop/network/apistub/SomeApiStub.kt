package me.uptop.network.apistub

import io.reactivex.Completable
import me.uptop.network.api.SomeApi

class SomeApiStub : SomeApi {
    override fun sendIncidents(): Completable = Completable.complete()
}