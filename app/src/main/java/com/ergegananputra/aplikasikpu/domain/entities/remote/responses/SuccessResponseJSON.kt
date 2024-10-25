package com.ergegananputra.aplikasikpu.domain.entities.remote.responses

open class SuccessResponseJSON <DATA> {
    var status: String? = null
    var message: String? = null
    open var data: DATA? = null
}
