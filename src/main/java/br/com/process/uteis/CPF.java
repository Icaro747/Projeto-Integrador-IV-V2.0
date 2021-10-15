package br.com.process.uteis;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Icaro
 */
@Slf4j
public class CPF {
    
    public static boolean Validar_CPF(String cpf){
        try {
            int CPF[] = new int[11];
            int mul[] = {11,10,9,8,7,6,5,4,3,2};
            int soma = 0, resto;
            String[] RS = cpf.split("");

            for (int i = 0; i < CPF.length; i++) {
                CPF[i] = Integer.parseInt(RS[i]);
            }

            if (VF(8, CPF)==true) {
                return false;
            }else{

                for (int i = 0; i < 9; i++) {
                    soma += (CPF[i] * mul[i+1]);
                }

                resto = (soma*10)%11;
                if (resto == 10) {
                    resto = 0;
                }

                if (resto != CPF[9]) {
                    return false;
                }else{

                    soma = 0;
                    for (int i = 0; i < 10; i++) {
                        soma += (CPF[i] * mul[i]);
                    }

                    resto = (soma*10)%11;
                    if (resto == 10) {
                        resto = 0;
                    }

                    return resto == CPF[10];
                }
            }
        } catch (NumberFormatException e) {
            log.error("letra ou um caractere especial no CPF");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Tamanho do CPF inválido");
            return false;
        }
    }
    
    public static boolean VF(int i, int CPF[]){
        if (i==1) {
            return CPF[i-1] == CPF[i];
        }else{
            return CPF[i-1] == CPF[i] && VF(i-1, CPF);
        }
    }
}
