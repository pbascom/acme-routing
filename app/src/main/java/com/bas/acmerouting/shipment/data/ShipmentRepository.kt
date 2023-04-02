package com.bas.acmerouting.shipment.data

/**
 * The Repository interface is responsible for retrieving the list of shipments from storage and
 * providing them to the UI. In this case, "storage" is an assets file, but there's no reason for
 * code in our ViewModel or below to know that.
 *
 * I'm making another choice here, which is to apply business logic *in* the repository. If I were
 * designing the entire tech stack, I would try to keep business logic off the client entirely;
 * ideally, the orchestration layer of a microservices architecture would match drivers with
 * shipments, while the BFF services would focus on providing those matched pairs efficiently to
 * multiple clients at scale, and the client would provide only the UI.
 *
 * In a real application, I would therefore expect to get (ideally) a single Destination object for
 * this particular logged-in Driver, or at worst a map of (Driver, Destination) pairs. The specifications
 * of this coding exercise make that impossible, but I'm going to build a Repository with an Adapter
 * layer which will allow me to act like I'm designing against my ideal network stack, anyway.
 *
 * This has two main benefits: 1, I get to write cleaner code with well-separated business rules
 * according to the architecture I (and my team) consider best; and 2, if we ever do move business
 * logic into the service, it will be extremely easy to refactor the client application to consume
 * the new contract.
 */
interface ShipmentRepository {
    suspend fun getShipments() : Map<Driver, Destination>
}

