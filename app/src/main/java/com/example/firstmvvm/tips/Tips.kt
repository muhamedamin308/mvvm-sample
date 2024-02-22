package com.example.firstmvvm.tips

fun main() {
    // Without with
    val salary = 1000
    calculateNetSalary(salary)
    calculateTaxis(salary)
    // With with
    with(salary) {
        calculateNetSalary(this)
        calculateTaxis(this)
    }
}



data class User(var name: String? = null, var email: String? = null)
fun calculateNetSalary(salary: Int) {}
fun calculateTaxis(salary: Int) {}