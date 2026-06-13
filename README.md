# Order Service — Spring Boot + DDD

API REST de gestión de órdenes construida con **Domain-Driven Design (DDD)**, Spring Boot 3 y PostgreSQL.

---

## Stack técnico

| Tecnología | Versión | Rol |
|---|---|---|
| Java | 17 | Lenguaje |
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | — | Persistencia |
| PostgreSQL | 16 | Base de datos |
| Lombok | 1.18 | Reducción de boilerplate |
| Springdoc OpenAPI | 2.5 | Documentación Swagger |
| Docker / Compose | — | Containerización |

---

## Arquitectura — DDD en 4 capas

```
src/
└── com.example.orderservice/
    ├── domain/           ← Núcleo: entidades, value objects, reglas de negocio
    │   └── order/
    │       ├── model/    ← Order (Aggregate Root), OrderItem, OrderStatus
    │       ├── repository/ ← Interfaz del repositorio (contrato)
    │       ├── service/  ← OrderDomainService
    │       └── exception/
    │
    ├── application/      ← Casos de uso: orquesta el dominio
    │   └── order/
    │       ├── usecase/  ← CreateOrderUseCase, UpdateOrderUseCase, ...
    │       ├── command/  ← Objetos de entrada (inmutables: records)
    │       └── response/ ← Objetos de salida
    │
    ├── infrastructure/   ← Adaptadores: JPA, mappers, implementación del repo
    │   └── persistence/
    │       ├── entity/   ← OrderEntity, OrderItemEntity (@Entity JPA)
    │       ├── mapper/   ← Domain ↔ Entity
    │       └── repository/ ← OrderRepositoryImpl, JpaOrderRepository
    │
    └── interfaces/       ← Entrada HTTP: controllers, DTOs, mappers REST
        └── rest/
            ├── OrderController.java
            ├── dto/
            ├── mapper/
            └── exception/
```

### Por qué esta separación importa

- **`domain`** no depende de ninguna otra capa. No importa Spring, no importa JPA.
- **`application`** depende solo del dominio. Llama al repositorio por su interfaz.
- **`infrastructure`** implementa las interfaces del dominio. Conoce JPA, Postgres, etc.
- **`interfaces`** traduce HTTP → comando de aplicación → respuesta HTTP.

---

## Levantar con Docker (recomendado)

### Prerrequisitos
- Docker Desktop instalado y corriendo

### Pasos

```bash
# 1. Compilar el JAR
mvn clean package -DskipTests

# 2. Construir la imagen Docker
docker build -t order-service:latest .

# 3. Levantar app + PostgreSQL
docker compose up -d

# 4. Ver logs
docker compose logs -f order-service
```

La API queda disponible en: `http://localhost:8080`  
Swagger UI en: `http://localhost:8080/swagger-ui.html`

### Comandos útiles

```bash
docker compose down          # Detener contenedores
docker compose down -v       # Detener y borrar datos de BD
docker compose ps            # Ver estado de los servicios
```

---

## Levantar en local (sin Docker)

Necesitas PostgreSQL corriendo localmente. Luego:

```bash
# Configura las variables de entorno (o edita application.yml para desarrollo)
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=orders_db
export DB_USERNAME=-
export DB_PASSWORD=-

mvn spring-boot:run
```

---

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/orders` | Crear una nueva orden |
| `GET` | `/orders` | Listar todas las órdenes |
| `GET` | `/orders/{id}` | Obtener orden por ID |
| `PUT` | `/orders/{id}` | Actualizar estado de una orden |

### Ejemplo — Crear orden

```json
POST /orders
{
  "customerId": 1,
  "items": [
    { "productId": 10, "quantity": 2, "price": 99.99 },
    { "productId": 11, "quantity": 1, "price": 49.50 }
  ]
}
```

### Ejemplo — Actualizar estado

```json
PUT /orders/1
{
  "status": "CONFIRMED"
}
```

**Estados válidos y transiciones:**

```
CREATED → CONFIRMED | PENDING | CANCELED
PENDING → CONFIRMED | CANCELED
CONFIRMED → (estado terminal)
CANCELED  → (estado terminal)
```

---

## Estados de la orden

| Estado | Descripción |
|---|---|
| `CREATED` | Orden recién creada |
| `CONFIRMED` | Orden confirmada (estado final) |
| `PENDING` | En espera de acción |
| `CANCELED` | Cancelada (estado final) |

---

## Variables de entorno

| Variable | Default | Descripción |
|---|---|---|
| `DB_HOST` | `localhost` | Host de PostgreSQL |
| `DB_PORT` | `5432` | Puerto de PostgreSQL |
| `DB_NAME` | `orders_db` | Nombre de la base de datos |
| `DB_USERNAME` | `-` | Usuario |
| `DB_PASSWORD` | `-` | Contraseña |
| `SERVER_PORT` | `8080` | Puerto del servidor |
| `JPA_DDL_AUTO` | `update` | Estrategia de DDL de Hibernate |

---

## Próximos pasos

- [ ] Validación con `@Valid` en los DTOs de request
- [ ] Paginación en `GET /orders`
- [ ] Tests unitarios del dominio (Order, OrderStatus)
- [ ] Tests de integración con Testcontainers
- [ ] Spring Security + JWT
- [ ] Eventos de dominio con Spring Events o Kafka
