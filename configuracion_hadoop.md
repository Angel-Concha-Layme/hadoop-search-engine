
# Configuración de Hadoop en Modo Pseudo-Distribuido

Este documento proporciona una guía detallada sobre la configuración de Hadoop en modo pseudo-distribuido.

## Índice

1. [Definir Variables de Entorno](#definir-variables-de-entorno)
2. [Configurar Hadoop](#configurar-hadoop)
3. [Inicializar HDFS](#inicializar-hdfs)
4. [Iniciar los Servicios](#iniciar-los-servicios)
5. [Crea tu Directorio Home en HDFS](#crea-tu-directorio-home-en-hdfs)
6. [Creación de Directorio Home en HDFS](#creación-de-directorio-home-en-hdfs)
7. [Acceder a esos directorios](#acceder-a-esos-directorios)
8. [Recursos Adicionales y FAQ](#recursos-adicionales-y-faq)

## Definir Variables de Entorno

**Descripción**: Las variables de entorno son cadenas que contienen información sobre el entorno para el sistema y el software que se ejecuta en el sistema. Sirven como atajos para referenciar ubicaciones y configuraciones específicas.

**Por qué es necesario**: 
- `HADOOP_HOME`: Esta es una referencia al directorio donde Hadoop está instalado. Es crucial porque muchos scripts y configuraciones en Hadoop hacen referencia a esta variable para localizar bibliotecas y otros archivos esenciales.
  
- `PATH`: Al agregar los directorios bin y sbin de Hadoop al PATH, permitimos ejecutar comandos de Hadoop desde cualquier ubicación en la línea de comandos sin especificar la ruta completa.

- Las otras variables como `HADOOP_MAPRED_HOME`, `HADOOP_COMMON_HOME`, etc., son referencias a componentes específicos de Hadoop y también se utilizan en configuraciones y scripts.

## Configurar Hadoop

**Descripción**: Hadoop utiliza una serie de archivos XML para su configuración. Estos archivos se encuentran en el subdirectorio `etc/hadoop` del directorio de instalación de Hadoop.

**Por qué es necesario**:
- `core-site.xml`: Define configuraciones centrales para Hadoop.
  - `fs.defaultFS`: Especifica el nombre del sistema de archivos (FS) que Hadoop debería usar. HDFS (Hadoop Distributed File System) es el sistema de archivos distribuido que utiliza Hadoop. `localhost:9000` indica que el NameNode (el servidor maestro de HDFS) se ejecuta en la misma máquina y escucha en el puerto 9000.
  
- `hdfs-site.xml`: Define configuraciones para HDFS.
  - `dfs.replication`: Especifica el número de copias de un archivo que se deben mantener en HDFS. En un modo pseudo-distribuido, solo hay un nodo, por lo que este valor se establece en 1.

## Inicializar HDFS

**Descripción**: Antes de poder utilizar HDFS, es necesario formatear el sistema de archivos, lo que implica inicializar la estructura de directorios y archivos que utiliza HDFS.

**Por qué es necesario**:
- `hdfs namenode -format`: El NameNode es el servidor maestro de HDFS, y esta instrucción básicamente lo prepara para su primera ejecución. Formatea las áreas de almacenamiento del NameNode, es decir, borra el sistema de archivos HDFS y comienza desde cero.

## Iniciar los Servicios

**Descripción**: Hadoop opera en una arquitectura maestro-esclavo. El NameNode y el ResourceManager son demonios maestros que gestionan el almacenamiento y el procesamiento, respectivamente. El DataNode y el NodeManager son demonios esclavos que manejan el almacenamiento y el procesamiento en cada nodo, respectivamente.

**Por qué es necesario**:
- `start-dfs.sh`: Este script inicia el sistema de archivos distribuido HDFS. Inicia tanto el NameNode como el DataNode.
  
- `start-yarn.sh`: YARN (Yet Another Resource Negotiator) es el administrador de recursos y el sistema de planificación de tareas de Hadoop. Este script inicia el ResourceManager y el NodeManager.

Puedes verificar que los nodos estén corriendo visitando:

* NameNode: http://localhost:9870/
* ResourceManager: http://localhost:8088/



## Crea tu Directorio Home en HDFS

**Descripción**: HDFS, el Hadoop Distributed File System, posee una estructura de directorios similar a sistemas UNIX. Es benéfico tener un directorio "home" personal para cada usuario para almacenar sus archivos y datasets.

**Por qué es necesario**:
- `hdfs dfs -mkdir /user/angel`: Así como en un sistema de archivos UNIX, tener un directorio individual para cada usuario facilita la administración de permisos y la organización de datos. Es común que las aplicaciones y trabajos de Hadoop almacenen datos en el directorio home del usuario dentro de HDFS.

## Acceder a esos directorios

**Descripción**: Una vez creado el directorio, puedes subir, bajar, listar y manejar archivos dentro de HDFS utilizando el comando `hdfs dfs`.

**Pasos**:
1. Listar el contenido de tu directorio home:
   ```bash
   hdfs dfs -ls /user/angel
   ```
2. Subir un archivo local a tu directorio en HDFS:
   ```bash
   hdfs dfs -put archivo_local.txt /user/angel/
   ```
3. Bajar un archivo de HDFS a tu sistema local:
   ```bash
   hdfs dfs -get /user/angel/archivo_local.txt .
   ```




**Interfaz Web**: El NameNode de HDFS tiene una interfaz web que proporciona información sobre el estado del cluster y te permite navegar por el sistema de archivos HDFS.

   - Puedes acceder a esta interfaz en: [http://localhost:9870/](http://localhost:9870/)
   - Haz clic en "Utilidades" o "Utilities" en la parte superior, y luego selecciona "Explorar el sistema de archivos" o "Browse the file system" para ver y navegar por el contenido de HDFS.

