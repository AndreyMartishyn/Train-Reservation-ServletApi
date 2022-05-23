
# Train-reservation project

Administrator is able to create/delete/edit list of Stations and Routes between them. Route has following information:

- departure station and time of departure 
- arrival station and time of arrival

User is able to search routes between stations. Result of search is a list of trains with the following lines:
- train #
- time/date and departure station
- route duration
- time/date and arrival station
- availability of free seats
- trip cost
- link to check route schedule
If User is not registered or is Administrator - he is not able to proceed buying procedure.


## Installation

Install my-project with npm

```bash
 some install guide in future
```
    
## Deployment

To deploy this project run

```
  some deploy info in future
```

## Technologies used

- Java 8+ as Backend
- Servlet API / JSTL 
- MySQL database
- Tomcat servlet container v.8.5.72-windows-x64
- BootStrap + JSP + HTML + CSS as Frontend
- Junit 4 + Mockito Api
- Log4j logging aspect
- Maven as building project tool

## Project brief info

- User has 2 roles. Customer and Admin
- Non-registered user is able to check certain resouces without authorization (by filter)
- Non-registered user is able to register and login.
- Registered user has access to resources based on the role.
- User is saved in session.
- After logout session is invalidated.
- Every user is able to make booking, but only customer can submit "buy" button. Other users will be redirected to login page(incl. Admin).
- Customer is able to choose stations, choose which class of carriage is prefereble, fill all neccessary details and press "submit".
- Ordered tickets are displayed in Customer`s cabinet with status "paid" or "pay" button to press.

- Admin is able to make crud operations with stations and routes.
- Admin is able to edit users info.

## Project implemented requirements

- All requests are passing through Front-Controller.
- Command pattern implemented for servlets separation 
- All input data is validated and messages are displayed accordingly
- Input data is validated using HTML and backend logic.
- i18n localization implemented
- Pagination implemented on admin page 
- Passwords are encoded using PBKDF2 hashing function
- Database connection using DataSource Tomcat JNDI
- Unit testing using Junit4 and Mockito (in progress)

