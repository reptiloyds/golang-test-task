PROD=docker-compose-prod.yml
DEV=docker-compose-dev.yml

up:
	docker compose -f ${PROD} up -d
down:
	docker compose -f ${PROD} down
down-v:
	docker compose -f ${PROD} down -v

upd:
	docker compose -f ${DEV} up -d
downd:
	docker compose -f ${DEV} down
downd-v:
	docker compose -f ${DEV} down -v
