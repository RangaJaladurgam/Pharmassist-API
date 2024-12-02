# Pharmassist-API
Pharmassist is an offline pharmacy management system that helps pharmacies manage inventory, billing, and transactions efficiently. The system supports features such as adding products via Excel, managing stock, creating bills, and integrating billing with patient records.
### Table of Contents
- _Features_
- _Technologies Used_
- _Usage_
- _Future Enhancements_

### Features
- **Pharmacy Management**: Add, update, and manage multiple pharmacies.
- **Inventory Management**: Upload medicines via Excel, handle stock quantities, and categorize products (e.g., medicines, supplements).
- **Billing System**:
  - Create and manage bills.
  - Add items to the bill while preventing duplicate entries.
  - Automatically adjust stock quantities based on purchases.
- **Patient Integration**: Link patients with billing and transaction history.
- **Admin Authentication**: Secure admin access using Spring Security.
- **Custom Queries**: Efficient database interactions.
- **Validation**: Ensure robust validation of input fields using Spring Validator and custom annotations.

### Technologies Used
- **Backend**: Spring Boot, Java
- **Database**: MySQL
- **Tools**:
  - _Apache POI_ for Excel processing
  - _iText_ and _Flying Saucer_ for PDF generation
  - _Postman_
- **Template Structuring**: _Thymeleaf_ (for generating structured PDF bills)
- **Security**: _Spring Security_
- **IDE**: Eclipse
- **Version Control**: GitHub
  
### Usage
#### Admin Authentication
- Access the authentication endpoints:
  - Register Admin: `/register`
  - Login Admin: `/login`
#### Medicine Management
- Upload medicines via Excel using the `/upload-medicines` endpoint.
#### Billing
- Add items to a bag and associate them with bills.
- Generate structured PDF bills using Thymeleaf templates.
#### API Testing
- Test API endpoints using tools like ***Postman***.

### Future Enhancements
- Add support for email notifications for low stock or expired medicines.
- Integrate with online payment gateways for future online store implementations.
- Add analytics for tracking sales, inventory, and customer trends.

---
