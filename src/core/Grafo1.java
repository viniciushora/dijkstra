package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class Grafo1 {
    public  final int  inf=999999;
    int tempo;
    public DirectedSparseMultigraph<String, EdgeType> getWg() {
        return wg;
    }
    public void setWg(DirectedSparseMultigraph<String, EdgeType> wg) {
        this.wg = wg;
    }	
    private DirectedSparseMultigraph<String, EdgeType > wg;
    /**
     * 
     * @param graphFile
     * carrega um grafo do arquivo de entrada
     */
    public void load(String graphFile) {
        this.wg=new DirectedSparseMultigraph<String, EdgeType> ();
        BufferedReader buffread;
        String linha;
        List<String> vertices;
        List<String> result;
        int wei;
        int i;
        try {
        /* obtem os v�rtices primeiro */
            buffread = new BufferedReader(new FileReader(graphFile));
            i=0;
            boolean eof=false;
            int vdest;
            /* obtem os v�rtices primeiro */
            linha=buffread.readLine();
            vertices = Arrays.asList(linha.split("\\s* \\s*"));
            for(String v: vertices) {
                this.wg.addVertex(v);
            }
            /* adiciona as arestas agora */
            i=0;
            while((linha=buffread.readLine())!=null) {
                result = Arrays.asList(linha.split("\\s* \\s*"));
                vdest=0;
                for(String w: result) {			         		
                    wei = Integer.valueOf(w);
                    if(wei!=0) {
                     EdgeType e = new EdgeType(wei,String.valueOf(this.wg.getEdgeCount()));
                     this.wg.addEdge(e, vertices.get(i), vertices.get(vdest));
                    } 
                    vdest++;
                }  				
               i++;
            }
            buffread.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
    }

    public void mostraGrafo1(DirectedSparseMultigraph g, String nomedoGrafo) {
        Layout<Integer, String> layout = new CircleLayout(g);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String i) {
                return Color.YELLOW;
            }
         };  
        Transformer<EdgeType,String> edgePaint = new Transformer<EdgeType,String>() {
            public String transform(EdgeType i) {
                return  String.valueOf(i.weight);
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(edgePaint);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        JFrame frame = new JFrame(nomedoGrafo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

/*
 * Esta visualiza��o ignora os pesos do grafo	
 */
    public void mostraGrafo2(DirectedGraph<String, EdgeType> g) {
        Layout<Integer, EdgeType> layout = new CircleLayout(g);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String i) {
                return Color.YELLOW;
            }
         };  
        Transformer<EdgeType,String> edgePaint = new Transformer<EdgeType,String>() {
            public String transform(EdgeType i) {
                return i.getLabel();
            }
        };       	     
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(edgePaint);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
        JFrame frame = new JFrame("Grafo de Depend�ncia do Caminho");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);   	    
    }

    class VData{
    	int cor, /* flag usado no algoritmo para cada v�rtice*/
    	td,      /* tempo de descoberta do v�rtice */
    	tt; /* tempo de termino do v�rtice */
        String pred; /* predecessor/antecessor do v�rtice na busca tanto no DFS quanto no BFS*/
        float dist;
    }
       
    public void DFS(DirectedGraph<String, EdgeType> g) {
        HashMap<String,VData> dVertices = new HashMap<String,VData>();
        /* inicializando os dados dos v�rtices */
        tempo= 0;
        for(String u: g.getVertices()) {
                VData vd = new VData();
                vd.cor=0; // 0 � branco
                vd.pred=null;
                dVertices.put(u, vd);
        }
        for(String u: g.getVertices()) {
                if(dVertices.get(u).cor==0) {// verifico se a cor � branca
                   visita(g,dVertices,u);
                }
        }

        /* a partir daqui o c�digo cria um grafo a partir do resultado do DFS */
        /*
         * Adiciono os v�rtices
         */
        DirectedGraph<String, EdgeType> gDFS=new DirectedSparseMultigraph<String, EdgeType> ();
        for(String u: g.getVertices()) {
                gDFS.addVertex(u);

        }
/*
 * adiciono as arestas 	    
 */
	   for(String u: g.getVertices()) {
		   String v =dVertices.get(u).pred;
		   if(v!=null) {
			   EdgeType e = new EdgeType(1);
			   gDFS.addEdge(e,v, u);
			   
		   }
	   }  
	   
	  /*
	   * chama o mostra grafo 2 para exibir o grafo gerado pelo conjunto de antecessores gerado pel DFS 
	   */
	   this.mostraGrafo2(gDFS);
   }
    
    /* m�todo visita do DFS
     *  
     */
	
	private void visita(DirectedGraph<String, EdgeType> g, HashMap<String, VData> dVertices, String u) {
		// TODO Auto-generated method stub
		
		VData ud = dVertices.get(u);
		ud.cor=1; //cinza
		tempo++;
		ud.td=tempo;
		for(String v: g.getNeighbors(u)) { // pega lista de adjacentes de u
			VData vd = dVertices.get(v);
			if (vd.cor==0) {// se a cor do adjacente � branco
				vd.pred=u;
				visita(g,dVertices,v);
			}
		}
		ud.cor=2; // pinta a cor do v�rtice visitado de preto quando termino seus adjacentes
		tempo++; // incremento mais uma vez o tempo
		ud.tt=tempo; // atribuo o tempo de termino para u
		
		
	}
	
	public List<String> obtemLOT(DirectedGraph<String, EdgeType> g) {
		   LinkedList<String> lot = new LinkedList<String>();
		   HashMap<String,VData> dVertices = new HashMap<String,VData>();
		   /* inicializando os dados dos v�rtices */
		   tempo= 0;
		   for(String u: g.getVertices()) {
			   VData vd = new VData();
			   vd.cor=0; // 0 � branco
			   vd.pred=null;
			   dVertices.put(u, vd);
		   }
		   for(String u: g.getVertices()) {
			   if(dVertices.get(u).cor==0) {// verifico se a cor � branca
			      visita(g,dVertices,u,lot);
			   }
		   }

		   /* a partir daqui o c�digo cria um grafo a partir do resultado do DFS */
		   /*
		    * Adiciono os v�rtices
		    */
		   DirectedSparseMultigraph<String, EdgeType> gDFS=new DirectedSparseMultigraph<String, EdgeType> ();
		   for(String u: g.getVertices()) {
			   gDFS.addVertex(u);

		   }
	/*
	 * adiciono as arestas 	    
	 */
		   for(String u: g.getVertices()) {
			   String v =dVertices.get(u).pred;
			   if(v!=null) {
				   EdgeType e = new EdgeType(1);
				   gDFS.addEdge(e,v, u);
				   
			   }
		   }  
		   
		  /*
		   * chama o mostra grafo 2 para exibir o grafo gerado pelo conjunto de antecessores gerado pel DFS 
		   */
		   this.mostraGrafo1(gDFS, "grafo da LOT");
		   return lot;
	   }

	private void visita(DirectedGraph<String, EdgeType> g, HashMap<String, VData> dVertices, String u, LinkedList<String> lot) {
		// TODO Auto-generated method stub
		
		VData ud = dVertices.get(u);
		ud.cor=1; //cinza
		tempo++;
		ud.td=tempo;
		for(String v: g.getNeighbors(u)) { // pega lista de adjacentes de u
			VData vd = dVertices.get(v);
			if (vd.cor==0) {// se a cor do adjacente � branco
				vd.pred=u;
				visita(g,dVertices,v,lot);
			}
		}
		ud.cor=2; // pinta a cor do v�rtice visitado de preto quando termino seus adjacentes
		tempo++; // incremento mais uma vez o tempo
		ud.tt=tempo; // atribuo o tempo de termino para u
		lot.addFirst(u);
		
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Grafo1 g = new Grafo1();
		//g.load("g1.txt");
		//g.mostraGrafo1(g.wg);
		//g.DFS(g.wg);
		
		//Teste que usa o m�todo BFS
				Grafo1 g2 = new Grafo1();
				g2.load("g1.txt");
				g2.mostraGrafo1(g2.wg);
				g2.BFS(g2.wg);


	}
*/
	

	public void BFS(DirectedGraph<String, EdgeType> g) {
            HashMap<String, VData> dVertices = new HashMap<>();
            for (String u : g.getVertices()) {
                VData vd = new VData();
                vd.cor = 0;
                vd.pred = null;
                dVertices.put(u, vd);
            }
            VData inicio = dVertices.get("A");
            inicio.cor = 1;
            Queue<String> fila = new LinkedList<>();
            List<VData> visitados = new ArrayList<>();
            List<VData> encontrados = new ArrayList<>();
            fila.add("A");
            encontrados.add(inicio);
            while (!fila.isEmpty()) {
                String u = fila.remove();
                visitaBFS(g, u, dVertices, visitados, encontrados, fila);
                visitados.add(dVertices.get(u));
            }
            DirectedGraph<String, EdgeType> gBFS = new DirectedSparseMultigraph<String, EdgeType>();
            for (String u : g.getVertices()) {
                gBFS.addVertex(u);
            }
            for (String u : g.getVertices()) {
                String v = dVertices.get(u).pred;
                if (v != null) {
                        EdgeType e = new EdgeType(1);
                        gBFS.addEdge(e, v, u);
                }
            }
            this.mostraGrafo2(gBFS);
	}

	public void visitaBFS(DirectedGraph<String, EdgeType> g, String u, Map<String, VData> dVertices,
			List<VData> visitados, List<VData> encontrados, Queue<String> fila) {
		for (String v : g.getNeighbors(u)) {
			VData vd = dVertices.get(v);
			if ((!visitados.contains(vd)) && (!encontrados.contains(vd))) {
				vd.cor = 1;
				vd.pred = u;
				encontrados.add(vd);
				fila.add(v);
			}
		}
		dVertices.get(u).cor = 2;
	}

	public static void testeBFS() {
            Grafo1 g = new Grafo1();
            g.load("g2.txt");
            g.mostraGrafo2(g.wg);
            g.BFS(g.wg);
	}
	public static void testeMenorCaminho() {
            Grafo1 g = new Grafo1();
            g.load("grafo100.txt");
            //g.mostraGrafo1(g.wg, "resultado Belman-ford");
            g.menorCaminhoorigemUnica(g.wg,"V1");
	}
        
        public static void testeDijkstra(){
            Grafo1 g = new Grafo1();
            g.load("grafo100.txt");
            g.mostraGrafo1(g.wg, "Grafo Padrão");
            g.dijkstra(g.wg,"V1");
        }
	
	
    public void menorCaminhoorigemUnica(DirectedGraph<String, EdgeType> g, String s) {
    	long startTime = System.currentTimeMillis();
    	HashMap<String, VData> dVertices = new HashMap<>();
        int arestasFinais = 0;
    	/* inicializa��o de vari�veis na estrutura VData */
		for (String u : g.getVertices()) {
		  VData vd = new VData();
		  vd.pred = null;
		  vd.dist=this.inf;
		  dVertices.put(u, vd);
		}
		VData inicio = dVertices.get(s);
		inicio.dist=0;  	
		bellman_ford(g,dVertices);
		/* apresentando os resultados */
		DirectedSparseMultigraph<String, EdgeType> gMC = new DirectedSparseMultigraph<String, EdgeType>();
		for (String u : g.getVertices()) {
			gMC.addVertex(u);
		}

		for (String u : g.getVertices()) {
			String v = dVertices.get(u).pred;
			if (v != null) {
				arestasFinais++;
				EdgeType e = new EdgeType(this.wg.findEdge(v, u).weight,String.valueOf(gMC.getEdgeCount()));
				gMC.addEdge(e, v, u);
			}
		}

		this.mostraGrafo1(gMC,"Resultado Belman-Ford");
        long endTime   = System.currentTimeMillis();
        long totalTime = (endTime - startTime);
        System.out.println("Arestas finais= " + arestasFinais);
        System.out.println("Tempo de Execução Belman= " + totalTime);
    }
    
	
    public void menorCaminhooGAO(DirectedGraph<String, EdgeType> g, String s) {
    	List<String> lot =this.obtemLOT(g);
    	HashMap<String, VData> dVertices = new HashMap<>();
    	/* inicializa��o de vari�veis na estrutura VData */
        for (String u : g.getVertices()) {
          VData vd = new VData();
          vd.pred = null;
          vd.dist=this.inf;
          dVertices.put(u, vd);

        }
        VData inicio = dVertices.get(s);
        inicio.dist=0;
        for(String u: lot) {
            for(String v: g.getNeighbors(u)) {
              EdgeType e = g.findEdge(u, v);
                if(e!=null) {
                    relax(u,v,g.findEdge(u, v).weight,dVertices);
                }   
            }
        }	
        //bellman_ford(g,dVertices);
        /* apresentando os resultados */
        DirectedSparseMultigraph<String, EdgeType> gMC = new DirectedSparseMultigraph<String, EdgeType>();
        for (String u : g.getVertices()) {
            gMC.addVertex(u);
        }
        for (String u : g.getVertices()) {
            String v = dVertices.get(u).pred;
            if (v != null) {
                EdgeType e = new EdgeType(this.wg.findEdge(v, u).weight,String.valueOf(gMC.getEdgeCount()));
                gMC.addEdge(e, v, u);
            }
        }
        this.mostraGrafo1(gMC,"resultado menorCaminho GAO");
    }
    
    public static void testeMenorCaminhoGAO() {
        Grafo1 g = new Grafo1();
        g.load("g3.txt");
        //g.mostraGrafo1(g.wg, "grafo de entrada");
        g.menorCaminhooGAO(g.wg,"A");
    }    
    
    public void relax(String u,String v, float w,HashMap<String, VData> dVertices) {
    	if( dVertices.get(v).dist> dVertices.get(u).dist + w ) {
            dVertices.get(v).dist= dVertices.get(u).dist + w;
            dVertices.get(v).pred=u;
    	}
    }
    public boolean bellman_ford(DirectedGraph<String, EdgeType> g,HashMap<String, VData> dVertices){
    	int i;
        int complexidade = 0;
    	for(i=0;i<g.getVertexCount();i++) {
    	   for(String u: g.getVertices()) { 
    	      for(String v: g.getNeighbors(u)) {
    	    	  EdgeType e = g.findEdge(u, v);
    	    	  if(e!=null) {
                    complexidade++;
    		    relax(u,v,g.findEdge(u, v).weight,dVertices);
    	    	  }   
    	      }
    	    }
    	}
        System.out.println("Complexidade= " + complexidade);
    	return false;
    }
    
    public void dijkstra(DirectedGraph<String, EdgeType> g, String s){
        long startTime = System.currentTimeMillis();
        HashMap<String, VData> dVertices = new HashMap<>();
        Queue<String> naoVisitados = new LinkedList<>();
        int complexidade = 0;
        int arestas = 0;
        int arestasFinais = 0;
        for (String u : g.getVertices()) {
          VData vd = new VData();
          vd.pred = null;
          vd.dist=this.inf;
          dVertices.put(u, vd);
          naoVisitados.add(u);
        }
        VData inicio = dVertices.get(s);
        inicio.dist=0;
        while (!naoVisitados.isEmpty()){
            String u = menorDistancia(dVertices, naoVisitados);
            naoVisitados.remove(u);
            for(String v: g.getNeighbors(u)) {
                EdgeType e = g.findEdge(u, v);
                if(e!=null) {
                    complexidade++;
                    arestas++;
                    relax(u,v,e.weight,dVertices);
                }
            }
        }
        DirectedSparseMultigraph<String, EdgeType> gDij = new DirectedSparseMultigraph<String, EdgeType>();
        for (String u : g.getVertices()) {
            gDij.addVertex(u);
        }
        for (String u : g.getVertices()) {
            String v = dVertices.get(u).pred;
            if (v != null) {
                EdgeType e = new EdgeType(this.wg.findEdge(v, u).weight,String.valueOf(gDij.getEdgeCount()));
                gDij.addEdge(e, v, u);
                arestasFinais++;
            }
        }
        final Runtime rt = Runtime.getRuntime();
        long endTime   = System.currentTimeMillis();
        long totalTime = (endTime - startTime);
        System.out.println("Complexidade Dijkstra = " + complexidade);
        System.out.println("Tempo de Execução Dijkstra= " + totalTime);
        System.out.println("Arestas totais= " + arestas);
        System.out.println("Arestas finais= " + arestasFinais);
        this.mostraGrafo1(gDij, "Menor Caminho Dijkstra");
    }
    
    public String menorDistancia(HashMap<String, VData> dVertices, Queue<String> naoVisitados){
        String vMenorDistancia = new String();
        float menorDistancia = 0;
        boolean primeiro = false;
        for (String u: naoVisitados){
            VData vd = new VData();
            if (primeiro == false){
                vMenorDistancia = u;
                menorDistancia = vd.dist;
                primeiro = true;
            } else if (vd.dist < menorDistancia ){
                vMenorDistancia = u;
                menorDistancia = vd.dist;
            }
        }
        return vMenorDistancia;
    }
    
	public static void main(String[] args) {
            //testeBFS();
            //testeMenorCaminho();
            testeDijkstra();
	}


 	
}