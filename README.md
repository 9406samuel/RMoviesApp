# RMoviesApp

### DESCRIPCIÓN DEL PROYECTO:

La aplicación se desarrolló bajo la arquitectura MVVM y empleando los componentes de arquitectura Room, ViewModel, LiveData y Databinding. Las capas de la aplicación se encuentran encapsuladas en su paquete correspondiente, lo cual permite tener cada funcionalidad aislada/encapsulada a nivel de paquete, una organización limpia del proyecto y la claridad de las capas existentes.

A continuación se describe cada una de las capas, paquete(s) y clase(s) correspondientes:

**Persistencia de datos:**
- **_room:_** Almacenamiento local de las películas con toda la información obtenida desde el API.
  - Clases:
    - *AppDatabase:* define la base de datos, tablas que la componen, versionamiento de la mismas, entre otros.
    - *MovieDao:* define los métodos/operaciones para interactuar con la base de datos.

**Negocio:**
- **_models:_** Entidades de la aplicación.
  - Clases:
    - **_entity_**
      - *Movie:* define la entidad para el modelado de la información en la base de datos.
    - **_network_**
      - *MovieResponse:* define la respuesta obtenida desde el API.

**Vistas:**
- **_view:_** Vistas de la aplicación.
  - **_main:_** Componentes para mostrar las peliculas por categoria.
    - Clases:
      - *MainActivity:* define la vista donde se muestran las películas obtenidas localmente/remotamente clasificadas por categorías.
      - *MainPagerAdapter:* define el Pager para la administración de las categorías(fragmentos) de películas.
      - **_category:_** Componentes para mostrar un listado de peliculas
        - Clases:
          - *MovieListFragment:* define la vista que contiene el listado de peliculas segun la categoria.
          - *MovieListAdapter:* define el Adapter para la administración de los ítems(pelicula) de cada listado de películas.
          - *MovieListViewModel:* define el ViewModel desde el cual se consume el API, se obtienen las películas almacenadas localmente y se cargan las películas obtenidas en los listados de películas.
          - *MovieViewModel:* define el ViewModel para persistir la información mostrada en cada ítem del listado de películas, responder al evento para mostrar el detalle de la película.
  - **_details:_** Componentes para mostrar los detalles de una película.
    - Clases:
      - *MovieDetailsActivity:* define la vista donde se muestra todo el detalle de una película.

**Red:**
- **_Api:_** Consumo del Api.
  - Clases:
    - *ApiClient:* define la configuración de Retrofit para consumir el API.
    - *MovieService:* define los métodos para obtener las películas y su información desde el API.

**Utilidades:**
- **_factory:_** Creacion de ViewModels.
  - Clases:
    - *ViewModelFactory:* define el Factory para obtener las instancias del ViewModel MovieListViewModel. Es requerido ya que el ViewModel en mención recibe parámetros de entrada.
  - **_utils:_** Constantes, convertidores de data, BindingsAdapter para los Databinding, Glide para la administración de las imágenes.
    - Clases:
      - *ApiConstants:* contiene los endpoint, operaciones, valores por defecto y demás información requerida para consumir el API.
      - *AppGlideModule:* clase requerida para la configuración de Glide.
      - *BindingAdapters:* define los BindingAdapter usados desde los Databinding para obtener y observar la información que se muestra en las vistas.
      - *Constants:* contiene constantes generales que se usan a través de la aplicación.
      - *Converters:* contiene los métodos requeridos para poder almacenar objetos compuestos en la base de datos.
      - *ViewExtension:* contiene el método con el cual se obtiene el contexto base de cualquier View. Requerido para la funcionalidad de los BindingAdapter.



### PRINCIPIO DE RESPONSABILIDAD UNICA:

En este contexto, se refiere a que un objeto debe realizar una única cosa, tener asignado un único rol dentro de la aplicación.
Al aplicar este principio se obtiene una aplicación desacoplada, flexible, fácil de probar y muy escalable, donde los cambios a los componentes existentes son mínimos o nulos, se evitan clases con muchas líneas de código y difíciles de mantener.



### CARACTERÍSTICAS DE UN BUEN CÓDIGO:

Un buen código o código limpio debe estar bien organizado y comentado/documentado correctamente. Lo anterior hace referencia principalmente a los siguientes aspectos:

- Nombres autodescriptivos o que den un indicio del propósito de la clase, variable, interfaz, método, entre otros.
- Los comentarios y documentación deben ser lo suficientemente completos para que alguien que no conoce el código, pueda entenderlo/usarlo/modificarlo/extenderlo.
- Todo lo que existe en el código tiene un propósito.
- El codigo esta indentado correctamente y no tiene lineas de codigo demasiado largas.
- Tiene aplicado el principio DRY(Don't Repeat Yourself). Fragmentos de código no se deben repetir en varias partes del código, sino que se deben unificar y hacer un llamado a éste desde donde se requiera.
- Los archivos deben estar bien organizados acorde a algun patron o arquitectura.
- Los modificadores de acceso deben estar bien definidos y no deben permitir comportamientos o llamados distintos a los que se deben tener.
