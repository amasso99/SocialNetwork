package Control;

import Model.Edge;
import Model.Graph;
import Model.List;
import Model.Vertex;

import java.util.stream.IntStream;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class MainController {

    //Attribute

    //Referenzen
    private Graph allUsers;

    public MainController(){
        allUsers = new Graph();
        createSomeUsers();
    }

    /**
     * Fügt Personen dem sozialen Netzwerk hinzu.
     */
    private void createSomeUsers(){
        insertUser("1");
        insertUser("2");
        insertUser("3");
        insertUser("4");
        insertUser("5");
        insertUser("6");

        befriend("1","2");
        befriend("1","3");


        befriend("2","4");


        befriend("5","6");


        befriend("3","5");

        befriend("4","3");

    }

    /**
     * Fügt einen Nutzer hinzu, falls dieser noch nicht existiert.
     * @param name
     * @return true, falls ein neuer Nutzer hinzugefügt wurde, sonst false.
     */
    public boolean insertUser(String name){
        //TODO 05: Nutzer dem sozialen Netzwerk hinzufügen.
        if(allUsers.getVertex(name) == null) {
            allUsers.addVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Löscht einen Nutzer, falls dieser existiert. Alle Verbindungen zu anderen Nutzers werden ebenfalls gelöscht.
     * @param name
     * @return true, falls ein Nutzer gelöscht wurde, sonst false.
     */
    public boolean deleteUser(String name){
        //TODO 07: Nutzer aus dem sozialen Netzwerk entfernen.
        Vertex temp = allUsers.getVertex(name);
        if(temp != null){
            allUsers.removeVertex(temp);
            return true;
        }
        return false;
    }

    /**
     * Falls Nutzer vorhanden sind, so werden ihre Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @return
     */
    public String[] getAllUsers(){
        //TODO 06: String-Array mit allen Nutzernamen erstellen.
        if(!allUsers.isEmpty()) {
            return vertexListToArray(allUsers.getVertices());
        }
        return null;
    }

    private String[] vertexListToArray(List<Vertex> list){
        String[] out = new String[getSize(list)];
        list.toFirst();
        for (int i = 0; i < out.length; i++) {
            out[i] = list.getContent().getID();
            list.next();
        }
        return out;
    }

    /**
     * Falls der Nutzer vorhanden ist und Freunde hat, so werden deren Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @param name
     * @return
     */
    public String[] getAllFriendsFromUser(String name){
        //TODO 09: Freundesliste eines Nutzers als String-Array erstellen.
        Vertex vertex = allUsers.getVertex(name);
        if(vertex != null){
            return vertexListToArray(allUsers.getNeighbours(vertex));
        }
        return null;
    }

    /**
     * Bestimmt den Zentralitätsgrad einer Person im sozialen Netzwerk, falls sie vorhanden ist. Sonst wird -1.0 zurückgegeben.
     * Der Zentralitätsgrad ist der Quotient aus der Anzahl der Freunde der Person und der um die Person selbst verminderten Anzahl an Nutzern im Netzwerk.
     * Gibt also den Prozentwert an Personen im sozialen Netzwerk an, mit der die Person befreundet ist.
     * @param name
     * @return
     */
    public double centralityDegreeOfUser(String name){
        //TODO 10: Prozentsatz der vorhandenen Freundschaften eines Nutzers von allen möglichen Freundschaften des Nutzers.
        Vertex temp = allUsers.getVertex(name);
        if(name != null){
            double allFriends = getSize(allUsers.getVertices())-1;
            double tempFriends = getSize(allUsers.getNeighbours(temp));
            return tempFriends/allFriends;
        }
        return -1.0;
    }

    /**
     * Zwei Nutzern des Netzwerkes gehen eine Freundschaft neu ein, falls sie sich im Netzwerk befinden und noch keine Freunde sind.
     * @param name01
     * @param name02
     * @return true, falls eine neue Freundeschaft entstanden ist, ansonsten false.
     */
    public boolean befriend(String name01, String name02){
        //TODO 08: Freundschaften schließen.
        Vertex f1 = allUsers.getVertex(name01);
        Vertex f2 = allUsers.getVertex(name02);
        if(f1 != null && f2 != null && allUsers.getEdge(f1,f2) == null){
            allUsers.addEdge(new Edge(f1,f2,0f));
            return true;
        }
        return false;
    }

    /**
     * Zwei Nutzer beenden ihre Freundschaft, falls sie sich im Netzwerk befinden und sie befreundet sind.
     * @param name01
     * @param name02
     * @return true, falls ihre Freundschaft beendet wurde, ansonsten false.
     */
    public boolean unfriend(String name01, String name02){
        //TODO 11: Freundschaften beenden.
        Vertex f1 = allUsers.getVertex(name01);
        Vertex f2 = allUsers.getVertex(name02);
        Edge edge = allUsers.getEdge(f1,f2);
        if(f1 != null && f2 != null && edge != null){
            allUsers.removeEdge(edge);
            return true;
        }
        return false;
    }

    /**
     * Bestimmt die Dichte des sozialen Netzwerks und gibt diese zurück.
     * Die Dichte ist der Quotient aus der Anzahl aller vorhandenen Freundschaftsbeziehungen und der Anzahl der maximal möglichen Freundschaftsbeziehungen.
     * @return
     */
    public double dense(){
        //TODO 12: Dichte berechnen.
        int amount = getSize(allUsers.getEdges());
        double posAmount = IntStream.range(1,getSize(allUsers.getVertices())).sum();

        return amount/posAmount;
    }

    /**
     * Gibt die möglichen Verbindungen zwischen zwei Personen im sozialen Netzwerk als Stirng-Array zurück,
     * falls die Personen vorhanden sind und sie über eine oder mehrere Ecken miteinander verbunden sind.
     * @param name01
     * @param name02
     * @return
     */
    public String[] getLinksBetween(String name01, String name02){
        Vertex user01 = allUsers.getVertex(name01);
        Vertex user02 = allUsers.getVertex(name02);
        if(user01 != null && user02 != null){
            //TODO 13: Schreibe einen Algorithmus, der mindestens ein Verbindung von einem Nutzer über Zwischennutzer zu einem anderem Nutzer bestimmt. Happy Kopfzerbrechen!
            //Tiefensuche

            List<Vertex> list = searchVertex(user01,user02);
            return vertexListToArray(list);
        }
        return null;
    }

    private List<Vertex> searchVertex(Vertex start, Vertex target) {
        List<Vertex> temp = new List<>();
        temp.append(start);
        start.setMark(true);
        List<Vertex> neighbours = allUsers.getNeighbours(start);
        if (!neighbours.isEmpty()) {
            neighbours.toFirst();
            while (neighbours.hasAccess()) {
                if (!neighbours.getContent().isMarked()) {
                    if (neighbours.getContent() != target) {
                        temp.concat(searchVertex(neighbours.getContent(), target));
                    } else {
                        temp.append(target);
                        neighbours.toLast();
                    }
                }
                neighbours.next();
            }
            return temp;
        }
        return new List<Vertex>();
    }

    public int getSize(List list){
        int counter = 0;
        list.toFirst();
        while(list.hasAccess()){
            counter++;
            list.next();
        }
        return counter;

    }
}
