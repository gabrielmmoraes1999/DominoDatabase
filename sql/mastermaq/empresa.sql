SELECT DISTINCT cnpj = (SELECT cnpj
                 FROM NG.dbo.bpm_dadospessoajuridica
                WHERE idpessoa = (SELECT idowner
                                    FROM NG_FOLHA.dbo.flh_registro
                                   WHERE idregistro = (SELECT idregistro
                                                         FROM NG_FOLHA.dbo.flh_folhapay
                                                        WHERE idpay = flh_folhapayverba.idpay)))
  FROM NG_FOLHA.dbo.flh_folhapayverba
 WHERE (SELECT idtipofolha
          FROM NG_FOLHA.dbo.flh_folhapay
         WHERE idpay = flh_folhapayverba.idpay) NOT IN (2, 20, 21) AND (SELECT competenciaano
																		  FROM NG_FOLHA.dbo.flh_folhapay
																		 WHERE idpay = flh_folhapayverba.idpay) >= ?