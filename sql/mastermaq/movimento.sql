SELECT cnpj = (SELECT cnpj
                 FROM NG.dbo.bpm_dadospessoajuridica
                WHERE idpessoa = (SELECT idowner
                                    FROM NG_FOLHA.dbo.flh_registro
                                   WHERE idregistro = (SELECT idregistro
                                                         FROM NG_FOLHA.dbo.flh_folhapay
                                                        WHERE idpay = flh_folhapayverba.idpay))),
       cod_empregado = (SELECT codigoregistro
                           FROM NG_FOLHA.dbo.flh_registro
                          WHERE idregistro = (SELECT idregistro
                                                FROM NG_FOLHA.dbo.flh_folhapay
                                               WHERE idpay = flh_folhapayverba.idpay)),
       ano = (SELECT competenciaano
                FROM NG_FOLHA.dbo.flh_folhapay
               WHERE idpay = flh_folhapayverba.idpay),
       mes = (SELECT competenciames
                FROM NG_FOLHA.dbo.flh_folhapay
               WHERE idpay = flh_folhapayverba.idpay),
       cod_rubrica = (SELECT codigoverba
                        FROM NG_FOLHA.dbo.flh_verba
                       WHERE flh_verba.idverba = flh_folhapayverba.idverba),
       referencia = qtdereferenciacalculada,
       referencia_info = qtdereferenciainformada,
	   valor = valorcalculado,
       valor_info = valorinformado,
       descr_rubrica = (SELECT descricaoverba
                          FROM NG_FOLHA.dbo.flh_verba
                         WHERE flh_verba.idverba = flh_folhapayverba.idverba),
       tipo_folha = (SELECT idtipofolha
                       FROM NG_FOLHA.dbo.flh_folhapay
                      WHERE idpay = flh_folhapayverba.idpay),
	   idverba,
       indprovdesc,
       indsistema,
       indusuario,
       indexcluido
  FROM NG_FOLHA.dbo.flh_folhapayverba
 WHERE (SELECT idtipofolha
          FROM NG_FOLHA.dbo.flh_folhapay
         WHERE idpay = flh_folhapayverba.idpay) NOT IN (2, 20, 21) AND (SELECT competenciaano
																		  FROM NG_FOLHA.dbo.flh_folhapay
																		 WHERE idpay = flh_folhapayverba.idpay) >= ?
ORDER BY ano DESC, mes DESC, cnpj DESC, cod_empregado DESC, cod_rubrica ASC