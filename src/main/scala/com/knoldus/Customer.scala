package com.knoldus

case class Customer(name: String, address : String, creditCardNumber : BigInt, mobileNumber : BigInt)

case object SamsungMobiles{
  override def toString: String = "Successful purchase"
}
