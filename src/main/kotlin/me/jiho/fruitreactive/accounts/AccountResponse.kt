package me.jiho.fruitreactive.accounts

data class AccountResponse(val id: Long?, val email: String, val appleInTree: Int, val appleInBasket: Int) {

    constructor(account: Account) : this(id = account.id, email = account.email, appleInTree = account.appleInTree, appleInBasket = account.appleInBasket)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountResponse

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}