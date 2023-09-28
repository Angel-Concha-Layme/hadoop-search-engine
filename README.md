
# Motor de búsqueda Hadoop

## Configuración del Cluster Hadoop 

### Prerrequisitos

#### I. Java:

Hadoop opera óptimamente con Java versión 8 o superior.

a) Actualización de Repositorios:
```bash
sudo apt update && sudo apt upgrade
```

b) Instalación de Java:
```bash
sudo apt install openjdk-11-jdk
```

### II. Adquisición e Instalación de Hadoop:

a) Adquisición del Paquete de Hadoop:
```bash
wget https://downloads.apache.org/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz
```

b) Extracción del Paquete:
```bash
tar -xzf hadoop-3.3.6.tar.gz
```

c) Migración del Directorio de Hadoop:
```bash
sudo mv hadoop-3.3.6 /usr/local/hadoop
```

Nota: Si bien la ubicación `/usr/local` es convencional, los usuarios pueden optar por un directorio distinto que se adapte a sus particularidades.

### III. Configuración del Entorno Hadoop:

#### i. Variables de Entorno:
Edición del archivo `.bashrc` para definición de variables:
```bash
nano ~/.bashrc
```

Incorporación de las siguientes declaraciones al final del documento:
```bash
export HADOOP_HOME=/usr/local/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
```

Posterior a la edición:
```bash
source ~/.bashrc
```

#### ii. Ajustes de Hadoop:
a) Modificación de `core-site.xml`:
```bash
nano /usr/local/hadoop/etc/hadoop/core-site.xml
```

Incorporación dentro de la etiqueta `<configuration>`:
```xml
<property>
    <name>fs.defaultFS</name>
    <value>hdfs://localhost:9000</value>
</property>
```

b) Modificación de `hdfs-site.xml`:
```bash
nano $HADOOP_HOME/etc/hadoop/hdfs-site.xml
```

Incorporación dentro de la etiqueta `<configuration>`:
```xml
<property>
    <name>dfs.replication</name>
    <value>1</value>
</property>
```

#### iii. Inicialización de HDFS:

a) Establecimiento del `JAVA_HOME`:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
source ~/.bashrc
```

b) Formateo del Sistema de Archivos (solo requerido en la primera instancia):
```bash
hdfs namenode -format
```

#### iv. SSH y JAVA_HOME:

a) SSH:

1. Adquisición e instalación de SSH:
```bash
sudo apt install openssh-server
```

2. Generación de Clave SSH:
```bash
ssh-keygen -t rsa -P ""
```

3. Incorporación de Clave SSH al archivo `authorized_keys`:
```bash
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys
```

4. Validación de funcionamiento SSH:
```bash
ssh localhost
```

b) JAVA_HOME en Hadoop:

1. Modificación del archivo `hadoop-env.sh`:
```bash
nano /usr/local/hadoop/etc/hadoop/hadoop-env.sh
```

2. Inclusión de línea de configuración:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
```

Nota: Es posible establecer `JAVA_HOME` de forma global, si se considera pertinente.

#### v. Activación de Servicios:

Inicio de NameNode, DataNode, ResourceManager, y NodeManager:
```bash
start-dfs.sh
start-yarn.sh
```

Validación de servicios activos:

- NameNode: [http://localhost:9870/](http://localhost:9870/)
- ResourceManager: [http://localhost:8088/](http://localhost:8088/)

Si NameNode es inaccesible, es probable que el directorio por defecto no exista. Verificar y, si es necesario, crear y ajustar permisos según se indica en los siguientes pasos.

1. Verificar si el directorio `/tpm/hadoop-<nombre_de_usuario>/dfs/name` existe:
```bash
ls /tmp/hadoop-<nombre_de_usuario>/dfs/name
```
2. Si no existe, crearlo:
```bash
mkdir -p /tmp/hadoop-<nombre_de_usuario>/dfs/name
```

3. Configurar permisos    
```bash
sudo chown -R <nombre_de_usuario>:<nombre_de_usuario> /tmp/hadoop-<nombre_de_usuario>
sudo chmod -R 755 /tmp/hadoop-<nombre_de_usuario>
```

4. Reiniciar HDFS
```bash
stop-dfs.sh
start-dfs.sh
```


#### vi. Directorio Principal en HDFS:

Creación de directorio:
```bash
hdfs dfs -mkdir /user
hdfs dfs -mkdir /user/<nombre_de_usuario>
```


---

Para más detalles sobre la configuración de Hadoop, consulta [este documento](./configuracion_hadoop.md).

---





## Conjunto de datos


## PageRank 


## 