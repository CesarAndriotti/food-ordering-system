# Food Ordering System - Arquitectura actual

Fecha: 2026-09-17
Ubicacion del proyecto: `food-ordering-system/`

## 1) Que es este proyecto

`food-ordering-system` es un proyecto Java multi-modulo con Maven que busca separar la logica de negocio del dominio (pedidos) de la infraestructura tecnica.

En su estado actual, el codigo implementado esta centrado en el **dominio del pedido** (`Order`, `OrderItem`, `Product`, `Money`, IDs y validaciones). Varios modulos de aplicacion e infraestructura ya existen en estructura, pero todavia estan con clases base tipo `org.example.Main`.

## 2) Como esta organizado (nivel alto)

En `food-ordering-system/pom.xml` se declaran dos modulos principales al mismo nivel:

- `common`
- `order-service`

Esto no significa que hagan lo mismo:

- **`common`**: biblioteca compartida (building blocks del dominio) reusable por varios servicios.
- **`order-service`**: bounded context de pedidos, donde vive la logica del caso de uso de ordenes.

## 3) Para que sirve `common`

`common` contiene submodulo:

- `common-domain`

### 3.1 `common-domain`

Es el modulo base compartido. Define conceptos genericos de DDD:

- `BaseEntity<ID>` y `AggregateRoot<ID>`
- `BaseId<T>`
- `DomainException`
- `DomainEvent<T>` (interfaz)
- value objects reutilizables:
  - `Money`
  - `OrderId`, `CustomerId`, `RestaurantId`, `ProductId`
  - `OrderStatus` (PENDING, PAID, APPROVED, CANCELLING, CANCELLED)

**En resumen:** `common-domain` evita duplicar clases base y reglas comunes entre servicios.

## 4) Para que sirve `order-service`

`order-service` agrega submodulos para separar responsabilidades por capas:

- `order-domain`
- `order-application`
- `order-data-access`
- `order-messaging`
- `order-container`

### 4.1 `order-domain`

Es un agregador de la capa de dominio del pedido. Contiene:

- `order-domain-core`
- `order-application-service`

#### 4.1.1 `order-domain-core`

Es el modulo con mas implementacion real hoy.

Contiene:

- Entidades: `Order`, `OrderItem`, `Product`
- Value objects del dominio order: `TrackingId`, `StreetAddress`, `OrderItemId`
- Excepcion de dominio: `OrderDomainException`

Dependencia principal:

- depende de `common-domain`

**Que hace `Order` actualmente:**

- Inicializa identificadores y estado (`initializeOrder` / `initializeId`)
- Valida consistencia del agregado:
  - estado inicial correcto
  - precio total mayor a cero
  - suma de subtotales de items igual al total de la orden
- Inicializa los items asignandoles `OrderId` y `OrderItemId`

**Que hace `OrderItem` actualmente:**

- valida precio del item contra `Product.price`
- valida subtotal (`price * quantity`)

#### 4.1.2 `order-application-service`

Su intencion arquitectonica es alojar servicios de aplicacion del dominio (orquestacion de casos de uso, puertos, coordinacion).

Estado actual observado:

- depende de `order-domain-core`
- aun no tiene servicios de negocio implementados (solo `Main` de ejemplo)

### 4.2 `order-application`

Capa de entrada para exponer casos de uso (ejemplo futuro: comandos, handlers, API adapters).

Estado actual observado:

- depende de `order-application-service`
- aun en estado esqueleto (solo `Main` de ejemplo)

### 4.3 `order-data-access`

Capa de persistencia (repositorios, mapeo JPA, acceso a BD).

Estado actual observado:

- depende de `order-application-service`
- aun en estado esqueleto (solo `Main` de ejemplo)

### 4.4 `order-messaging`

Capa de integracion asincrona (eventos, brokers, publishers/consumers).

Estado actual observado:

- depende de `order-application-service`
- aun en estado esqueleto (solo `Main` de ejemplo)

### 4.5 `order-container`

Modulo de composicion/arranque. Debe cablear todo (application + data + messaging + domain) y levantar el servicio.

Estado actual observado:

- depende de `order-domain-core`, `order-application-service`, `order-application`, `order-data-access`, `order-messaging`
- aun en estado esqueleto (solo `Main` de ejemplo)

## 5) Como funciona hoy (flujo real disponible)

Hoy el flujo con implementacion fuerte esta en la validacion del agregado `Order` dentro de `order-domain-core`.

Flujo conceptual:

1. Se crea una `Order` con `customerId`, `restaurantId`, `deliveryAddress`, `price` e `items`.
2. `validateOrder()` comprueba reglas de negocio:
   - orden en estado inicial
   - total mayor a cero
   - total == suma de subtotales
3. `initializeOrder()` asigna:
   - `OrderId`
   - `TrackingId`
   - estado `PENDING`
   - ids internos de cada `OrderItem`

Resultado: el agregado queda consistente antes de persistirse/publicarse por capas externas.

## 6) Relacion entre `common` y `order-service`

- `common` provee el **lenguaje base** del dominio (entidades base, ids, money, excepciones).
- `order-service` implementa el **dominio especifico de pedidos** y luego lo conecta con application, data y messaging.

Dependencia importante:

- `order-domain-core` usa `common-domain`.
- La capa de aplicacion e infraestructura de pedidos se apoya en `order-application-service` para evitar depender directo del dominio en todos lados.

## 7) Lectura arquitectonica rapida

Si quieres entender el proyecto rapido, este orden ayuda:

1. `common/common-domain`
2. `order-service/order-domain/order-domain-core`
3. `order-service/order-domain/order-application-service`
4. `order-service/order-application`
5. `order-service/order-data-access`
6. `order-service/order-messaging`
7. `order-service/order-container`

## 8) Estado actual y siguientes pasos recomendados

Estado actual:

- La base de dominio de pedidos esta empezada y tiene reglas utiles.
- Varias capas ya tienen estructura Maven correcta, pero todavia sin implementacion funcional completa.

Siguientes pasos sugeridos:

1. Definir puertos (interfaces) en `order-application-service`.
2. Implementar casos de uso en `order-application`.
3. Implementar repositorios/adaptadores en `order-data-access`.
4. Implementar eventos en `order-messaging`.
5. Cablear todo y exponer endpoint en `order-container`.

---

## Anexo A - Mapa de modulos

- `food-ordering-system` (root, packaging `pom`)
  - `common` (packaging `pom`)
    - `common-domain`
  - `order-service` (packaging `pom`)
    - `order-domain` (packaging `pom`)
      - `order-domain-core`
      - `order-application-service`
    - `order-application`
    - `order-data-access`
    - `order-messaging`
    - `order-container`

## Anexo B - Dependencias clave observadas

- `order-domain-core` -> `common-domain`
- `order-application-service` -> `order-domain-core`
- `order-application` -> `order-application-service`
- `order-data-access` -> `order-application-service`
- `order-messaging` -> `order-application-service`
- `order-container` -> `order-domain-core`, `order-application-service`, `order-application`, `order-data-access`, `order-messaging`

