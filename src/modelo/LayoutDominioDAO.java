package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import util.Sistema;

/**
 *
 * @author Gabriel Moraes
 */
public class LayoutDominioDAO {

    public ArrayList<String> retornaLancamentosPreparados(ArrayList<LayoutDominio> ldList) {
        ArrayList<String> linhas = new ArrayList<>();
        
        Sistema s = new Sistema();
        for(LayoutDominio ld : ldList) {
            String competencia = ld.getCompetencia();
            competencia = competencia.replaceAll("/", "");
            competencia = competencia.replaceAll("-", "");
            competencia = s.completeToLeft(competencia, '0', 6);
            String mes = competencia.substring(0, 2);
            String ano = competencia.substring(2, 6);
            
            String valor = ld.getValor();
            valor = valor.replaceAll(",", "");
            valor = valor.replaceAll("\\.", "");
            
            if(!"000000000".equals(s.completeToLeft(valor, '0', 9))) {
                linhas.add("10"+s.completeToLeft(String.valueOf(ld.getCodEmpregado()), '0', 10)+ano+mes+s.completeToLeft(ld.getCodRubrica(), '0', 4)+"11"+s.completeToLeft(valor, '0', 9)+s.completeToLeft(String.valueOf(ld.getCodEmpresa()), '0', 10));
            }
        }
        
        return linhas;
    }
    
    public ArrayList<String> removerEstagiarios(String arquivo, ArrayList<Empregado> listEmpregado) throws UnsupportedEncodingException, IOException {
        ArrayList<String> linhas = new ArrayList<>();
        
        Sistema s = new Sistema();
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "Cp1252"));
        String linha = buffRead.readLine();
        
        while (linha != null) {
            for(Empregado e : listEmpregado) {
                if(linha.substring(33, 43).equals(s.completeToLeft(String.valueOf(e.getCodEmpresa()), '0', 10)) && linha.substring(2, 12).equals(s.completeToLeft(String.valueOf(e.getCodigo()), '0', 10))) {
                    linhas.add(linha);
                }
            }
            
            linha = buffRead.readLine();
        }
        
        buffRead.close();
        return linhas;
    }
}
