# 🏆 Dux_Tennis_Match_Prediction 

## 🔎 Sobre el programa
Este programa, creado íntegramente en Java, es parte de la evaluación técnica en el proceso de selección en DuxSoftware. Es un simulador de juegos de tenis con 2 jugadores la cual le permite al usuario determinar el resultado del partido mediante el ingreso de una predicción.

## 📝 Aclaraciones para utilización
Para jugar un partido en el simulador, primero se ingresan los nombres del torneo y de los dos jugadores participantes. Luego, se establece la probabilidad de cada jugador de ganar el partido, teniendo en cuenta que las probabilidades van de 0% a 100% y son complementarias. Finalmente, se elige si se quiere jugar un partido de 3 o 5 sets.

Una vez que se han fijado estos parámetros, se pulsa el botón "Continuar" para iniciar la simulación. Una ventana se abre para mostrar el desarrollo del partido, incluyendo quién saca en cada set y cuántos puntos, games y sets han ganado cada jugador. Durante la simulación, el usuario sólo puede ver el progreso.

Cuando la simulación haya finalizado, se muestra un resumen del partido con los resultados de cada set y el ganador. El usuario puede optar por jugar una revancha con los mismos jugadores y parámetros o volver al menú principal para comenzar otra simulación diferente.

## 📖 Reglas
En este programa de tenis, se siguen las reglas básicas que incluyen:

 -Intercambio de saques después de cada set.
 -Primer saque sorteado al azar.
 -Saques intercalados luego del primer set.
 -Opción de jugar 3 o 5 sets.
 -Cada set consta de 6 games.
 -Ganar un game requiere pasar 40 puntos con una ventaja de 2.
 -Puntos se miden en 0-15-30-40-game.
 -Si los puntos empatan 40-40, el game se juega con una ventaja de 2.
 -Si los games empatan 5-5, el set se juega a 7 con una ventaja de 2 para ganar.
 -Si los games empatan 6-6, entra en juego el 'tie break' con mínimo 7 puntos y se gana con una ventaja de 2.
 -Si el 'tie break' empata 6-6, se resuelve con una ventaja de 2.
 -Partidos a 3 sets: tercer set no se juega si un jugador gana 2 sets consecutivos.
 -Partidos a 5 sets: cuarto set no se juega si un jugador gana 3 sets consecutivos.
 -Partidos a 5 sets: quinto set no se juega si un jugador gana 3 sets y el otro sólo 1.

## 💬 Comentarios
 -El botón "Continuar" en la ventana de parámetros no está disponible al inicio. Para activarlo, se requiere ingresar todos los nombres solicitados (escribir en el campo de texto y presionar la tecla "Enter").
 -Los nombres de los jugadores y el torneo deben estar compuestos solo de letras de la A a la Z (incluyendo la Ñ), en mayúsculas o minúsculas, con o sin acentos, con o sin espacios, y no deben tener más de 10 caracteres en total.

 ## 📥 Descarga
Para descargar este programa haga click aquí: 

## 📸 Capturas de pantalla
![Menu](./src/main/res/img/readme/ss1.png)\
*Ventana del menú principal*

![Input](./src/main/res/img/readme/ss2.png)\
*Ventana de ingreso de datos*

![simulation](./src/main/res/img/readme/ss3.png)\
*Ventana de progreso de simulación de partido*

![result](./src/main/res/img/readme/ss4.png)\
*Ventana de resultado de partido*


