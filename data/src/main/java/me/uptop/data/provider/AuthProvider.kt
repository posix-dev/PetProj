package me.uptop.data.provider

import io.reactivex.Observable

interface AuthProvider {
    var token: String?

    var verificationSessionId: String

    var dataMaybeInconsistent: Boolean

    fun isAuthorizedObservable(): Observable<Boolean>
}