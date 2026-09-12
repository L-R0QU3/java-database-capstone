# Módulo 5: Containerización y CI/CD - Evidencias

## Docker

### Dockerfile (multi-stage)
- **Etapa 1:** Maven + JDK 17 (compila el proyecto).
- **Etapa 2:** JRE 17 Alpine (imagen ligera de runtime).

### Imagen construida
- Nombre: `smart-clinic-backend`
- Comando: `docker build -t smart-clinic-backend .`

### Docker Compose
Levanta 3 servicios:
- `smartcare-mysql` (puerto 3306)
- `smartcare-mongo` (puerto 27017)
- `smartcare-backend` (puerto 8080)

Comando: `docker compose up -d --build`

## GitHub Actions (CI)

4 workflows configurados en `.github/workflows/`:

| Workflow | Propósito |
|----------|-----------|
| `lint-frontend.yml` | Lint HTML, CSS, JS con htmlhint, stylelint, eslint |
| `lint-backend.yml` | Checkstyle sobre código Java |
| `compile-backend.yml` | Compilación con Maven (`mvn clean compile`) |
| `lint-docker.yml` | Hadolint sobre el Dockerfile |

## Comandos ejecutados

```bash
# Construir imagen
docker build -t smart-clinic-backend .

# Levantar todo el stack
docker compose up -d --build

# Verificar contenedores
docker ps

# Detener
docker compose down
