package br.com.process.uteis;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Icaro
 */
public class Formulas {
    
    /**
     * <b>Arredondando</b> de números quebrados
     * @param valor valor da Serra Redonda
     * @param CasasDecimais quantidade de casas decimais
     * @return Valor arredondado
     */
    public static double Arredondando(double valor, int CasasDecimais){
        
        BigDecimal BD = new BigDecimal(valor).setScale(CasasDecimais, RoundingMode.HALF_EVEN);
        
        return BD.doubleValue();
    }
    
}
