### 1. Definir Variables de Entorno:

**Descripción**: Las variables de entorno son cadenas que contienen información sobre el entorno para el sistema y el software que se ejecuta en el sistema. Sirven como atajos para referenciar ubicaciones y configuraciones específicas.

**Por qué es necesario**: 
- `HADOOP_HOME`: Esta es una referencia al directorio donde Hadoop está instalado. Es crucial porque muchos scripts y configuraciones en Hadoop hacen referencia a esta variable para localizar bibliotecas y otros archivos esenciales.
  
- `PATH`: Al agregar los directorios bin y sbin de Hadoop al PATH, permitimos ejecutar comandos de Hadoop desde cualquier ubicación en la línea de comandos sin especificar la ruta completa.

- Las otras variables como `HADOOP_MAPRED_HOME`, `HADOOP_COMMON_HOME`, etc., son referencias a componentes específicos de Hadoop y también se utilizan en configuraciones y scripts.

### 2. Configurar Hadoop:

**Descripción**: Hadoop utiliza una serie de archivos XML para su configuración. Estos archivos se encuentran en el subdirectorio `etc/hadoop` del directorio de instalación de Hadoop.

**Por qué es necesario**:
- `core-site.xml`: Define configuraciones centrales para Hadoop.
  - `fs.defaultFS`: Especifica el nombre del sistema de archivos (FS) que Hadoop debería usar. HDFS (Hadoop Distributed File System) es el sistema de archivos distribuido que utiliza Hadoop. `localhost:9000` indica que el NameNode (el servidor maestro de HDFS) se ejecuta en la misma máquina y escucha en el puerto 9000.
  
- `hdfs-site.xml`: Define configuraciones para HDFS.
  - `dfs.replication`: Especifica el número de copias de un archivo que se deben mantener en HDFS. En un modo pseudo-distribuido, solo hay un nodo, por lo que este valor se establece en 1.

### 3. Inicializar HDFS:

**Descripción**: Antes de poder utilizar HDFS, es necesario formatear el sistema de archivos, lo que implica inicializar la estructura de directorios y archivos que utiliza HDFS.

**Por qué es necesario**:
- `hdfs namenode -format`: El NameNode es el servidor maestro de HDFS, y esta instrucción básicamente lo prepara para su primera ejecución. Formatea las áreas de almacenamiento del NameNode, es decir, borra el sistema de archivos HDFS y comienza desde cero.

### 4. Iniciar los Servicios:

**Descripción**: Hadoop opera en una arquitectura maestro-esclavo. El NameNode y el ResourceManager son demonios maestros que gestionan el almacenamiento y el procesamiento, respectivamente. El DataNode y el NodeManager son demonios esclavos que manejan el almacenamiento y el procesamiento en cada nodo, respectivamente.

**Por qué es necesario**:
- `start-dfs.sh`: Este script inicia el sistema de archivos distribuido HDFS. Inicia tanto el NameNode como el DataNode.
  
- `start-yarn.sh`: YARN (Yet Another Resource Negotiator) es el administrador de recursos y el sistema de planificación de tareas de Hadoop. Este script inicia el ResourceManager y el NodeManager.


Puedes verificar que los nodos estén corriendo visitando:

* NameNode: http://localhost:9870/
* ResourceManager: http://localhost:8088/


### 5. Crea tu Directorio Home en HDFS:

**Descripción**: HDFS, como un sistema de archivos, tiene su propia estructura de directorios. Es útil tener un directorio "home" para cada usuario para almacenar sus archivos y datos.

**Por qué es necesario**:
- `hdfs dfs -mkdir /user/angel`: Al igual que en un sistema de archivos UNIX, tener un directorio para cada usuario facilita la gestión de permisos y la organización de los datos. Es común que las aplicaciones y trabajos de Hadoop almacenen datos en el directorio home del usuario en HDFS.

En resumen, la configuración de Hadoop en modo pseudo-distribuido es un proceso que imita un entorno distribuido real en una sola máquina. Aunque se ejecuta en una sola máquina, cada componente de Hadoop se ejecuta en su propio proceso Java, y HDFS y YARN funcionan como si estuvieran operando en un clúster real. Esta configuración es excelente para el desarrollo y la prueba porque proporciona un entorno que se asemeja estrechamente a un clúster de producción, pero sin la complejidad de múltiples máquinas.


---


### Creación de Directorio Home en HDFS

El Hadoop Distributed File System (HDFS) es un sistema de archivos distribuido que forma parte de Hadoop. Es similar a otros sistemas de archivos, pero está diseñado para ser altamente resistente y trabajar con grandes conjuntos de datos distribuidos a través de múltiples nodos/máquinas. HDFS no está en tu sistema de archivos local; en cambio, vive dentro del ecosistema de Hadoop.

El comando `hdfs dfs` es una interfaz para interactuar con HDFS. Piensa en esto como una versión de Hadoop de comandos estándar de Linux para trabajar con archivos, pero específicamente para HDFS.

Ahora, el comando:
```bash
hdfs dfs -mkdir /user
```
está creando un directorio llamado "user" en la raíz de HDFS.

El siguiente comando:
```bash
hdfs dfs -mkdir /user/angel
```
está creando un subdirectorio llamado "angel" dentro del directorio "user" que acabamos de crear en HDFS. 

La razón de esto es establecer un espacio de trabajo para el usuario "angel" en HDFS, similar a tener un directorio home en un sistema Linux/Unix. En muchos trabajos de Hadoop y otras operaciones, los datos se leerán o escribirán en un directorio asociado con el usuario actual. Establecer este directorio home en HDFS permite que esas operaciones se realicen sin problemas.

### Acceder a esos directorios:

1. **Interfaz de línea de comandos**: Puedes usar el comando `hdfs dfs` para interactuar con HDFS. Por ejemplo:

   - Listar el contenido de tu directorio home:
     ```bash
     hdfs dfs -ls /user/angel
     ```

   - Copiar un archivo local al HDFS:
     ```bash
     hdfs dfs -copyFromLocal <archivo_local> /user/angel/
     ```

   - Ver los archivos en HDFS:
     ```bash
     hdfs dfs -cat /user/angel/<nombre_del_archivo>
     ```

2. **Interfaz Web**: El NameNode de HDFS tiene una interfaz web que proporciona información sobre el estado del cluster y te permite navegar por el sistema de archivos HDFS.

   - Puedes acceder a esta interfaz en: [http://localhost:9870/](http://localhost:9870/)
   - Haz clic en "Utilidades" o "Utilities" en la parte superior, y luego selecciona "Explorar el sistema de archivos" o "Browse the file system" para ver y navegar por el contenido de HDFS.

Espero que eso aclare las cosas. El concepto principal es que HDFS es un sistema de archivos separado dentro de Hadoop y el comando `hdfs dfs` es cómo interactuas con él desde la línea de comandos.