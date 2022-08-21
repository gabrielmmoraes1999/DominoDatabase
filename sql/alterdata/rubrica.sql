SELECT (select null as cnpj),
	   cdchamada,
	   nmevento,
	   sttipo,
	   stnatureza,
	   stunidade,
	   sttipoevento,
	   cdcampotrct,
	   vlpercentualtrct
FROM wdp.evento
order by cdchamada