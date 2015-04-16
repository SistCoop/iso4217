# iso4217 - restfull services

Es un paquete que permite administrar datos de países segun el estandar iso4217.

> Revisar iso4217 para mayor informacion.

### Resources
* Currency ("/currency")

| Currency                                                                                                  |
| Method        | URL                                               | Descripcion                           |
| :------------ |:--------------------------------------------------|:--------------------------------------|
| GET           | "/currency/alphabeticCode/{alpha2Code}"           | Busca uno segun alphabeticCode        |
| GET           | "/currency/numericCode/{numericCode}"             | Busca uno segun numericCode           |
| POST          | "/currency"                                       | Crea uno                              |
| PUT           | "/currency/alphabeticCode/{alphabeticCode}"       | Actualiza uno segun alphabeticCode    |
| PUT           | "/currency/numericCode/{numericCode}"             | Actualiza uno segun numericCode       |
| DELETE        | "/currency/alpha2Code/{alphabeticCode}"           | Elimina uno segun alphabeticCode      |
| DELETE        | "/currency/alpha2Code/{numericCode}"              | Elimina uno segun numericCode         |
| GET           | "/currency?filterText&firstResult&maxResults"     | Busca segun parametros                |

| Denominations                                                                                                 |
| Method        | URL                                                   | Descripcion                           |
| :------------ |:------------------------------------------------------|:--------------------------------------|
| GET           | "/currency/alphabeticCode/{alpha2Code}/denominations" | Denominaciones segun alphabeticCode   |
| GET           | "/currency/numericCode/{numericCode}/denominations"   | Denominaciones segun numericCode      |

Los objetos countryCodes tienen la siguiente estructura:

```json
"currency": {
    "entity": "String"
	"currency": "String"
	"alphabeticCode": "String"
	"numericCode": "String"
	"minorUnit": Integer
}
```

```json
"denomination": {
    "value": "BigDecimal"
}
```

### Version
1.0.0

### Tecnologías

Dependences:

* [JavaEE] - javaEE

Este proyecto es mantenido por SistCoop S.A.C.

License
----

MIT