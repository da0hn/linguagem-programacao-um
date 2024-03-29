package provaDois.agendaDeContatos.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import javax.management.InstanceNotFoundException;

import br.edu.ifmt.cba.util.Teclado;
import provaDois.agendaDeContatos.model.entities.Contato;
import provaDois.agendaDeContatos.model.entities.ContatoComercial;
import provaDois.agendaDeContatos.model.entities.ContatoPessoal;

public class Agenda {
	
	private static List contatos = new ArrayList();
	private static Iterator it;
	
	public static void main(String[] args) {
		menu();
	}
	
	private static void tabelaDeOpcoes() {
		System.out.println("<-===-> Agenda de Contatos <-===->");
		System.out.println("---------------------------------");
		System.out.println("1 - incluir");
		System.out.println("2 - excluir");
		System.out.println("3 - alterar");
		System.out.println("4 - consultar todos");
		System.out.println("5 - clonar");
		System.out.println("6 - sair");
		System.out.println("---------------------------------");
	}
	
	private static void menu() {
		tabelaDeOpcoes();
		int opcao;
		try {
			opcao = Teclado.leInt("O que deseja fazer? ");
		
			switch( opcao ) {
			case 1:
				incluir();
				break;
			case 2:
				excluir();
				break;
			case 3:
				alterar();
				break;
			case 4:
				consultarTodos();
				break;
			case 5:
				clonar();
				break;
			case 6:
				System.exit(0);
			default:
//				System.out.println("Opcao incorreta.");
				throw new InputMismatchException("mensagem"); 
			}
			
		} catch (InputMismatchException e) {
			System.out.println("Op��o inserida n�o � valida: " + e.getMessage());
		}
		finally {
			menu();
		}
		
	}

	private static Contato buscarContato(Integer codigo) {
		
		it = contatos.iterator();
		
		while( it.hasNext() ) {
			Contato c = (Contato)it.next();
			if( c.getCodigo() == codigo ) {
				return c;
			}
		}
		
		return null;
	}
	
	private static void clonar() {
		try {
			int codigo = Teclado.leInt("Insira o codigo do contato para clonagem: ");
			Contato c = buscarContato(codigo);
			
			if( c == null ) {
				System.out.println("Ocorreu um erro na sua clonagem, codigo inv�lido.");
				return;
			}
			
			inserirNaAgenda(c.clonar());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("N�o � possivel inserir um novo dado na agenda: " + e.getMessage() );
		} catch (InputMismatchException e) {
			System.out.println("c�digo do contato n�o � v�lido: " + e.getMessage() );
			clonar();
		}
		
	}

	private static void consultarTodos() {
		
		it = contatos.iterator();
		
		while( it.hasNext() ) {
			Contato c = (Contato)it.next();
			System.out.println("---------------------");
			System.out.println(c.verInformacoes());
		}
		
	}

	private static void alterar() {
		int codigo;
		try {
			codigo = Teclado.leInt("Insira o codigo do contato para alteracao: ");
			it = contatos.iterator();
			while( it.hasNext() ) {
				Contato contato = (Contato)it.next();
				if( codigo == contato.getCodigo() ) {
					
					String nome = Teclado.leString("Insira o novo nome: ");
					String email = Teclado.leString("Insira o novo email: ");
					String telefone = Teclado.leString("Insira o novo telefone: ");
					
					if( nome.isBlank() || nome.isEmpty() ) {
						throw new IllegalStateException();
					}
					else if( email.isBlank() || email.isEmpty() ) {
						throw new IllegalStateException();
					}
					else if( telefone.isBlank() || telefone.isEmpty() ) {
						throw new IllegalStateException();
						
					}
					
					contato.setNome(nome);
					contato.setEmail(email);
					contato.setTelefone(telefone);
					
					if( contato instanceof ContatoComercial ) {
						ContatoComercial c = (ContatoComercial) contato;
						c.setNomeDaEmpresa( Teclado.leString("Insira o novo nome da empresa: ") );
						c.setTelefoneComercial( Teclado.leString("Insira o novo telefone comercial: ") );
						contato = c;
					}
					else if( contato instanceof ContatoPessoal ) {
						ContatoPessoal p = (ContatoPessoal) contato;
						p.setDataDeAniversario( Teclado.leString("Insira nova data de aniversario"));
						contato = p;
					}
					else {
						throw new InstanceNotFoundException();
					}
					return;
				}
			}
//			System.out.println("Erro ao alterar, contato n�o encontrado");
		
			throw new InstanceNotFoundException();
		
		} catch (InputMismatchException e) {
			System.out.println("O c�digo inserido n�o � v�lido: " + e.getMessage());
			alterar();
		} catch( InstanceNotFoundException e) {
			System.out.println("N�o foi possivel alterar o contato: " + e.getMessage());
		}
		
	}

	private static void excluir() {
		try {
			int codigo = Teclado.leInt("Insira o codigo do contato para excluir: ");
			it = contatos.iterator();
			while( it.hasNext() ) {
				Contato c = (Contato)it.next();
				if( codigo == c.getCodigo() ) {
					System.out.println("Excluido com sucesso: ");
					System.out.println(c.verInformacoes());
					it.remove();
					return;
				}
			}
			
			throw new InstanceNotFoundException();
		} catch (InputMismatchException e) {
			System.out.println("c�digo inserido n�o � v�lido: " + e.getMessage() );
			excluir();
		} catch( InstanceNotFoundException e) {
			System.out.println("O contato do c�digo inserido n�o foi encontrado " + e.getMessage());
		}
	}
	
	private static void incluir() {
		
		try {
			System.out.println("1 - Contato Comercial");
			System.out.println("2 - Contato Pessoal");
			
			int opcao = Teclado.leInt("Insira sua opcao: ");
			
			Contato c;
			
			String nome = Teclado.leString("Insira o nome: ");
			String email = Teclado.leString("Insira o email: ");
			String telefone = Teclado.leString("Insira o telefone: ");
			
			if( opcao == 1 ) {
				String nomeDaEmpresa = Teclado.leString("Insira o nome da empresa: ");
				String telefoneComercial = Teclado.leString("Insira o telefone comercial: ");
				c = new ContatoComercial(nome, email, telefone, nomeDaEmpresa, telefoneComercial);
				inserirNaAgenda(c);
			}
			else if( opcao == 2) {
				String dataDeAniversario = Teclado.leString("Insira a data de anivers�rio: ");
				c = new ContatoPessoal(nome, email, telefone, dataDeAniversario);
				inserirNaAgenda(c);
			}
			else {
				System.out.println("Opcao invalida");
			}
			return;
		} catch ( IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( InputMismatchException e) {
			System.out.println("Op��o inserida n�o � v�lida");
			incluir();
		}
	}

	private static void inserirNaAgenda(Contato c) {
		
		contatos.add(c);
		
	}
	
}
