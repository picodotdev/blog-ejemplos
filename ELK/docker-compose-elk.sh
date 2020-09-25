$ # Para evitar este error que impide iniciar Elasticsearch  hay que cambiar esta configuración en el sistema (solo afecta a la ejecución actual, en el siguiente inicio no se conserva)
$ # [1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
$ sudo sysctl -w vm.max_map_count=262144

$ docker-compose -f docker-compose-elk.yml up