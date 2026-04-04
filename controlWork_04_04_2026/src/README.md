docker pull guzykkkkk/f-mobile-site:latest
docker run -p 8080:8080 guzykkkkk/f-mobile-site:latest
## Docker

Сборка образа:

```bash
docker build -t f-mobile-site .
```

Запуск:

```bash
docker run -p 8080:8080 f-mobile-site
```

После старта приложение будет доступно по адресу:

```text
http://localhost:8080/f-mobile-site/
```

## REST API

Все REST-эндпоинты требуют заголовок:

```text
X-Best-Security: f-mobile-super-secret
```

### Получить список опций комплектации

```http
GET /f-mobile-site/api/models/{modelId}/trims/{trimId}/options
```

Пример:

```bash
curl -H "X-Best-Security: f-mobile-super-secret" \
  http://localhost:8080/f-mobile-site/api/models/1/trims/1/options
```

### Добавить опцию

```http
POST /f-mobile-site/api/models/{modelId}/trims/{trimId}/options
Content-Type: application/json
```

Тело:

```json
{
  "name": "Электродвигатель",
  "price": 99000
}
```

Пример:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -H "X-Best-Security: f-mobile-super-secret" \
  -d '{"name":"Электродвигатель","price":99000}' \
  http://localhost:8080/f-mobile-site/api/models/1/trims/3/options
```

### Обновить опцию

```http
PUT /f-mobile-site/api/models/{modelId}/trims/{trimId}/options/{optionId}
Content-Type: application/json
```

Тело:

```json
{
  "name": "Климат-контроль",
  "price": 43000
}
```

### Удалить опцию

```http
DELETE /f-mobile-site/api/models/{modelId}/trims/{trimId}/options/{optionId}
```


