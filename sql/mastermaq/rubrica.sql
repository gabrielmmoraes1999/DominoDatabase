SELECT cnpj = (SELECT null),
	   codigo = (SELECT codigoverba
				   FROM NG_FOLHA.dbo.flh_verba
				  WHERE idverba = NG_FOLHA.dbo.flh_rubricaesocial.idverba),
	   descricaorubricaesocial,
	   idnaturezarubrica,
       idtiporubrica,
	   percentual = (SELECT percentual FROM NG_FOLHA.dbo.flh_verba WHERE idverba = NG_FOLHA.dbo.flh_rubricaesocial.idverba)
  FROM NG_FOLHA.dbo.flh_rubricaesocial
 WHERE idverba IN (SELECT DISTINCT idverba
								  FROM NG_FOLHA.dbo.flh_folhapayverba
								 WHERE (SELECT idtipofolha
										  FROM NG_FOLHA.dbo.flh_folhapay
										 WHERE idpay = flh_folhapayverba.idpay) NOT IN (2, 20, 21) AND (SELECT competenciaano
																										  FROM NG_FOLHA.dbo.flh_folhapay
																										 WHERE idpay = flh_folhapayverba.idpay) >= ?)
ORDER BY codigo