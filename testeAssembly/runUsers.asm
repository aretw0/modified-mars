.include "macros.asm"

.data
# Definição de arquivos
file1: .asciiz "fileSystem/arquivo1.txt" #com diretorio, precisa existir
#file1: .asciiz "arquivo1.txt" # sem diretório escreve na raiz do projeto
file2: .asciiz "fileSystem/arquivo2.txt"
file3: .asciiz "fileSystem/arquivo3.txt"

# body writing
body1: .asciiz "The quick brown fox jumps over the lazy dog " # tem 44 caracteres
chunk1: .asciiz "The quick b" # tem 11 caracteres
chunk2: .asciiz "rown fox ju" # tem 11 caracteres
chunk3: .asciiz "mps over th" # tem 11 caracteres
chunk4: .asciiz "e lazy dog " # tem 11 caracteres

# Saida
output: .asciiz ""

# linefeed
endl: .asciiz "\n"
# Avisos sucesso
openOk: .asciiz "Arquivo aberto\n"
closeOk: .asciiz "Arquivo fechado\n"
writeOk: .asciiz "Escrita concluída\n"
readOk: .asciiz "Leitura concluída\n"

# Avisos falha
warnO: .asciiz "Falha na abertura\n"
warnW: .asciiz "Falha na escrita\n"
warnR: .asciiz "Falha na leitura\n"

.text
#criação dos processos
	fork(Programa1, Programa2, 8, 0, 15)
	fork(Programa2, Programa3, 10, 0, 15)
	fork(Programa3, FimPrograma3, 12, 0, 15)
	fork(Idle,Programa1, 6, 0, 15)		
#escalonando o primeiro processo	
	processChange

Idle:

	loop:
		
	processChange

	j loop

Programa1: # programa tenta escrever no arquivo1
	openFile(file1, 9, 0)
	bge $v0,0, rightOpen1 # se for negativo deu falha
	prinThis(warnO)
	j fim1
rightOpen1:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writeFile(chunk1,11)
	bge $v0,0, rightWrite11 # se for negativo deu falha
	prinThis(warnW)
	j fim1
rightWrite11:
	prinThis(writeOk)
	writeFile(chunk2,11)
	bge $v0,0, rightWrite12 # se for negativo deu falha
	prinThis(warnW)
	j fim1
rightWrite12:
	prinThis(writeOk)
	writeFile(chunk3,11)
	bge $v0,0, rightWrite13 # se for negativo deu falha
	prinThis(warnW)
	j fim1
rightWrite13:
	prinThis(writeOk)
	writeFile(chunk4,11)
	bge $v0,0, rightWrite14 # se for negativo deu falha
	prinThis(warnW)
	j fim1
rightWrite14:
	prinThis(writeOk)
fim1:
	closeFile
	prinThis(closeOk)
	processTerminate

Programa2:
	openFile(file2, 9, 0)
	bge $v0,0, rightOpen2 # se for negativo deu falha
	prinThis(warnO)
	j fim2
rightOpen2:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writeFile(chunk1,11)
	bge $v0,0, rightWrite21 # se for negativo deu falha
	prinThis(warnW)
	j fim2
rightWrite21:
	prinThis(writeOk)
	writeFile(chunk2,11)
	bge $v0,0, rightWrite22 # se for negativo deu falha
	prinThis(warnW)
	j fim2
rightWrite22:
	prinThis(writeOk)
	writeFile(chunk3,11)
	bge $v0,0, rightWrite23 # se for negativo deu falha
	prinThis(warnW)
	j fim2
rightWrite23:
	prinThis(writeOk)
	writeFile(chunk4,11)
	bge $v0,0, rightWrite24 # se for negativo deu falha
	prinThis(warnW)
	j fim2
rightWrite24:
	prinThis(writeOk)
fim2:
	closeFile
	prinThis(closeOk)
	processTerminate

Programa3:
	openFile(file3, 9, 0)
	bge $v0,0, rightOpen3 # se for negativo deu falha
	prinThis(warnO)
	j fim3
rightOpen3:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writeFile(chunk1,11)
	bge $v0,0, rightWrite21 # se for negativo deu falha
	prinThis(warnW)
	j fim3
rightWrite31:
	prinThis(writeOk)
	writeFile(chunk2,11)
	bge $v0,0, rightWrite32 # se for negativo deu falha
	prinThis(warnW)
	j fim3
rightWrite32:
	prinThis(writeOk)
	writeFile(chunk3,11)
	bge $v0,0, rightWrite33 # se for negativo deu falha
	prinThis(warnW)
	j fim3
rightWrite33:
	prinThis(writeOk)
	writeFile(chunk4,11)
	bge $v0,0, rightWrite34 # se for negativo deu falha
	prinThis(warnW)
	j fim3
rightWrite34:
	prinThis(writeOk)
fim3:
	closeFile
	prinThis(closeOk)
	processTerminate
FimPrograma3:
