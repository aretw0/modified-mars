#macro para exit
.macro done
	li $v0,10
	syscall
.end_macro

#macro para imprimir string
.macro prinThis(%str)
    li $v0, 4
    la $a0, %str #lembrar de la
    syscall
.end_macro

#macro para fork
.macro fork (%val1, %val, %val2, %val3, %val4) 
	#parâmetros referenciados %val
	li $v0,18	
	la $a0, %val1
	la $a1, %val
	la $v1, %val2
	la $a3, %val3
	la $a2, %val4
	syscall
.end_macro

#macro para processChange
.macro processChange
	li $a0, 1
	li $v0,19
	syscall
.end_macro

#macro para processTerminate
.macro processTerminate
	li $a0, 1
	li $v0,20
	syscall
.end_macro
