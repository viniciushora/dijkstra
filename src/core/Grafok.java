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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class Grafok {
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
		  
		  
			buffread = new BufferedReader(new FileReader(graphFile));
			i=0;
			boolean eof=false;
			
			linha=buffread.readLine();
			vertices = Arrays.asList(linha.split("\\s* \\s*"));
			for(String v: vertices) {
				this.wg.addVertex(v);
				
			}
			/* adiciona as arestas agora */
			i=0;
			while((linha=buffread.readLine())!=null) {
				result = Arrays.asList(linha.split("\\s* \\s*"));
				for(String v: result) {
					wei = Integer.valueOf(v);
					if(wei!=0) {
   					 EdgeType e = new EdgeType(wei);
					 this.wg.addEdge(e, vertices.get(i), vertices.get(result.indexOf(v)));
					} 

				}  				
			  
			   i++;
			   System.out.print("reg" + i + ": ");
			   System.out.println(result);
			   
			}
			buffread.close();
	                
       }

	  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
} 
	
	@Override
public String toString() {
	return "Grafo1 [tempo=" + tempo + ", wg=" + wg + ", getWg()=" + getWg() + ", getClass()=" + getClass()
			+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
}
	/* for (String v: this.getGdep().getVertices()) {
		this.gdep.addVertex(v);
	}
	
	for ( Number e: c.getGdep().getEdges()) {
		Pair s= c.gdep.getEndpoints(e);
		String v1 = (String) s.getFirst();
		String v2 = (String) s.getSecond();
		this.gdep.addEdge(this.gdep.getEdgeCount(),v1, v2);
	}	
}
*/
	public void mostraGrafo1(DirectedSparseMultigraph g) {
		 // SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
	      // Layout<V, E>, VisualizationComponent<V,E>
		 Layout<Integer, String> layout = new CircleLayout(g);
	     layout.setSize(new Dimension(300,300));
	     //BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
	     BasicVisualizationServer vv = new BasicVisualizationServer<>(layout);
	     vv.setPreferredSize(new Dimension(350,350));       
	     // Setup up a new vertex to paint transformer...
	     Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
	         public Paint transform(String i) {
	             return Color.YELLOW;
	         }
	     };  
	     Transformer<EdgeType,String> edgePaint = new Transformer<EdgeType,String>() {
	         public String transform(EdgeType i) {
	        	    return  String.valueOf(i.getWeight());
	             
	         }
	     };
	     vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	    // vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
	     vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	     
	     vv.getRenderContext().setEdgeLabelTransformer(edgePaint);
	     //vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	     vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
	     
	     JFrame frame = new JFrame("Visualiza��o de um Grafo Ponderado");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.getContentPane().add(vv);
	     frame.pack();
	     frame.setVisible(true);   	    
		
	}

/*
 * 	
 */
	public void mostraGrafo2(DirectedGraph<String, EdgeType> g) {
		 // SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
	      // Layout<V, E>, VisualizationComponent<V,E>
		 Layout<Integer, EdgeType> layout = new CircleLayout(g);
	     layout.setSize(new Dimension(300,300));
	    // BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer,String>(layout);
	     BasicVisualizationServer vv = new BasicVisualizationServer<>(layout);
	     vv.setPreferredSize(new Dimension(350,350));       
	     // Setup up a new vertex to paint transformer...
	     Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
	         public Paint transform(String i) {
	             return Color.YELLOW;
	         }
	     };  
	     Transformer<EdgeType,String> edgePaint = new Transformer<EdgeType,String>() {
	         public String transform(EdgeType i) {
	        	    return  "";
	             
	         }
	     };       	     
	     vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	     vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	     vv.getRenderContext().setEdgeLabelTransformer(edgePaint);
	    // vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	     // vv.getRenderContext().getEdgeIncludePredicate();
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
    	
    String	pred; /* predecessor/antecessor do v�rtice na busca tanto no DFS quanto no BFS*/
        int dist;
    	
    }
    public void printVData(VData vd) {
    	System.out.println("cor pred td tt");
    	System.out.println(vd.cor + "    "+ vd.pred + "   "+ vd.td + "   "+ vd.tt);
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
		   printVData(dVertices.get(u));
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

	//Criei esse metodo auxiliar tambem
	public void inicializaVertices(DirectedGraph<String, EdgeType> g, HashMap<String, VData> dVertices) 
	{
		for (String u : g.getVertices()) 
		{
            VData vd = new VData();
            vd.cor = 0;
            vd.pred = null;
            dVertices.put(u, vd);
        }
	}
	
	public void BFS(DirectedGraph<String, EdgeType> g) 
	{
        HashMap<String, VData> dVertices = new HashMap<>();
		Queue<String> queue = new LinkedList<>();
        List<String> noVisit = new ArrayList<>();
        List<String> foundNos = new ArrayList<>();
		inicializaVertices(g, dVertices);
        VData primeiro = dVertices.get("A");
        primeiro.cor = 1;

        queue.add("A");
        foundNos.add("A");
        while (!queue.isEmpty()) 
		{
            String u = queue.remove();
            visitaBFS(g, u, dVertices, queue, foundNos, noVisit);
            noVisit.add(u);
        }

        DirectedGraph<String, EdgeType> grafico = new DirectedSparseMultigraph<String, EdgeType>();
        for (String u : g.getVertices()) 
		{
            printVData(dVertices.get(u));
            grafico.addVertex(u);

        }

        for (String u : g.getVertices()) 
		{
            String v = dVertices.get(u).pred;
            if (v != null) 
			{
                EdgeType e = new EdgeType(1);
                grafico.addEdge(e, v, u);
            }
        }

        this.mostraGrafo2(grafico);
    }

    public void visitaBFS(
		DirectedGraph<String, EdgeType> g, String u, Map<String, VData> dVertices, 
		Queue<String> queue, List<String> foundNos, List<String> noVisit
	) 
	{
        for (String v : g.getNeighbors(u)) {
            if ((!noVisit.contains(v)) && (!foundNos.contains(v))) {
				VData no = dVertices.get(v);
                no.cor = 1;
                no.pred = u;
                foundNos.add(v);
                queue.add(v);
            }
        }
        VData noAtual = dVertices.get(u);
		noAtual.cor = 2;
    }

	public static void teste_bfs()
	{
		Grafok grafo_bfs = new Grafok();
        grafo_bfs.load("g1.txt");
        grafo_bfs.mostraGrafo1(grafo_bfs.wg);
        grafo_bfs.BFS(grafo_bfs.wg);
	}

    public static void main(String[] args) {
        
		teste_bfs();
    }
}
