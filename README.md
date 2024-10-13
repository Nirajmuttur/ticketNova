
# TicketNova: Ticket Trading with a Bid-Based Marketplace

TicketNova is an online platform designed to simplify ticket trading for events like concerts, sports, and theater. It allows users to buy and sell tickets through a flexible bid-based system, giving buyers the option to either bid for tickets at their preferred price or purchase them instantly. Built using modern technologies like **Spring Boot**, **PostgreSQL**, **Next.js**, and **Apache Kafka**.

## Technical Architecture
TicketNova follows a microservice-based architecture, ensuring each component operates independently for scalability and fault isolation. Key components include:

1.**User Service**: Manages user sign-ups, logins, and verification with a dedicated database for secure credential storage.

2.**API Gateway(authentication service)**: Routes requests to the appropriate services and ensures security for all user interactions.

3.**TicketService**: Handles ticket listings, allowing sellers to set prices or enable bidding for their tickets.

4.**BidService**: Manages the bidding process, letting buyers place bids and sellers choose whether to accept.

5.**Kafka Integration**: Provides real-time messaging between services, ensuring timely bid updates and notifications for users. 

## Milestones
1.**User Service**: 
- **POST /api/v1/user/create**: Registers a new user.
  
2.**API Gateway(Authentication Service)**:
- **POST /api/v1/auth/authenticate**: Authenticates a user and returns a token.
  
3.**Ticket Service**: 
- **POST /api/v1/ticket/create**: Creates a new ticket listing.
- **GET /api/v1/ticket**: Retrieves all valid ticket listings.
- **GET /api/v1/ticket/{id}**: Retrieves details of a specific ticket by ID.
- **GET /api/ticket/u/{id}/{pageNumber}/{numberOfItems}**: Get tickets by seller id.
  
4.**Bid Service**: 
- **POST /api/v1/bid/create**: Places a new bid on a ticket.
- **PUT /api/v1/bid/{bidid}**: Updates the bid.

## Prerequisites
Before running this service locally, ensure you have the following tools installed:
- **Java 17 or above**
- **PostgreSQL**
- **Apache Kafka**

## PostgreSQL Database Configuration
**spring.datasource.url**=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>
**spring.datasource.username**=<YOUR_DB_USERNAME>
**spring.datasource.password**=<YOUR_DB_PASSWORD>

## JWT Secret Key
**jwt.secret**=<YOUR_JWT_SECRET_KEY>


### Clone the Repository

```bash
git clone https://github.com/Nirajmuttur/ticketNova
cd ticketNova


