package net.osable.core.model

class ContactRequest(
    private val name: String,
    private val email: String,
    private val message: String
) {

    fun asContentString() = "Name: $name\nEmail: $email\nMessage: $message"

}