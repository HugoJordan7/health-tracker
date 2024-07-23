package com.example.healthtracker.common.base

interface BaseView<P: BasePresenter>{
    val presenter: P
    fun displayFailure(message: String)
}