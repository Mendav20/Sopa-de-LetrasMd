package com.minsait.data.technologies.sopadeletras;

import java.io.IOException;

import javax.swing.JOptionPane;

public class InicioSopa {
	
	public InicioSopa() {}
		
		public void DatosSopa() throws IOException {
			String tamanioCuadricula = JOptionPane.showInputDialog("Ingrese el tamaño de la cuadricula: ");
			String nivelSel = JOptionPane.showInputDialog("Seleccione nivel de dificultad: \n1: Facil\n2:Medio\n3:Dificil");
			
			Cuadricula sopaDeLetrasVacia = new Cuadricula(Integer.valueOf(tamanioCuadricula));
			InsertadorDePalabras nuevoInsertador = new InsertadorDePalabras();			
			Cuadricula sopaDeLetrasConNivel;
			
			switch (nivelSel) {
			case "1":
				
			        sopaDeLetrasConNivel = nuevoInsertador.insertarNivelFacil(sopaDeLetrasVacia, "normal");
			        sopaDeLetrasConNivel.imprimirCuadricula();
				
				break;
			case "2":
			    sopaDeLetrasConNivel = nuevoInsertador.insertarNivelMedio(sopaDeLetrasVacia);
		        sopaDeLetrasConNivel.imprimirCuadricula();
				break;
			case "3":
				sopaDeLetrasConNivel = nuevoInsertador.insertarNivelDificil(sopaDeLetrasVacia);
		        sopaDeLetrasConNivel.imprimirCuadricula();
				break;

			default:
				JOptionPane.showMessageDialog(null, "Opcion No Valida!");
				break;
			}
		}
}
