package com.example.healthtracker.base

interface BaseView<P: BasePresenter>{
    val presenter: P
    fun displayFailure(message: String)
}