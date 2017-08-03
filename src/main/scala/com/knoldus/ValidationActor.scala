package com.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class ValidationActor(purchaseActRef : ActorRef) extends Actor with ActorLogging {

  var availableItems =1000

  override def receive = {
    case customer : Customer =>{
      if(availableItems>0){
        availableItems -=1
        log.info("Validation Actor Forwarding to Purchase Actor")
        purchaseActRef forward customer
      }
      else {
        log.error("Out of stock")
        sender() ! "out of stock"
      }
    }
  }

}

object ValidationActor{
   def props(purchase:ActorRef):Props =Props(classOf[ValidationActor],purchase)
}