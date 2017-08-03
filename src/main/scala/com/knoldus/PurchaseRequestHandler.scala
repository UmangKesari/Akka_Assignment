package com.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class PurchaseRequestHandler(purchaseRef: ActorRef) extends Actor with ActorLogging{

  override def receive = {
    case customer : Customer=> {
      log.info("successfull transaction")
      purchaseRef forward customer
    }

    case _ => log.error("Incomplete details")
      sender() ! "Please fill all details"
  }
}

object PurchaseRequestHandler {
  def props(validRef : ValidationActor):Props  = Props(classOf[PurchaseRequestHandler],validRef)
}