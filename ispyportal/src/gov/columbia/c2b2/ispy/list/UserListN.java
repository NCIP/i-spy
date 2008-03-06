/**
 * The UserListN will define an uploaded list by name, the type of list and the 
 * contained list itself.
 */
package gov.columbia.c2b2.ispy.list;

import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rossok
 *
 */
public class UserListN implements Serializable{ 
    /**
     * 
     */
    private static final long serialVersionUID = 5729349209858477497L;
    private Long id;
    private String name = "";
    private ListType listType;
    //a list can actually have several sub-types
    private ListSubType listSubType;
    private ListOrigin listOrigin;
    //user attached notes for a list
    private String notes;
    //private List<String> list = new ArrayList<String>();
    //private List<String> invalidList = new ArrayList<String>();
    private List<ListItem> list = new ArrayList<ListItem>();
    private List<ListItem> listItems = new ArrayList<ListItem>();

    
    private Set<ListItem> listItemsT = new HashSet<ListItem>();
    
//    private Set<ListItem> list = new HashSet<ListItem>();
    
    private List<ListItem> invalidList = new ArrayList<ListItem>();
    private List<ListItem> invalidListItems = new ArrayList<ListItem>();
    private Date dateCreated;
    private int itemCount = 0;
    private String author;
    private String institute;
    private String category;
    private char shareStatus;
    private Long userId;
    
    
    

    //newest constructor to replace string listsd with listitem lists
 
    public UserListN(String name, ListType listType, List<ListItem> list, List<ListItem> invalidList){
        this.name = name;
        this.listType = listType;
        this.list = list;
        this.invalidList = invalidList;        
    }
        
    /**
     * @deprecated
     */
    public UserListN(){}
    
    //base constructor: no subtype, no notes
    /**
     * @deprecated
     */
    public UserListN(String name, ListType listType, List<String> list, List<String> invalidList, Date dateCreated){
        this.name = name;
        this.listType = listType;
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String string: list){
            this.list.add(new ListItem(string,name));
        }
        for(String invalidString: invalidList){
            this.invalidList.add(new ListItem(invalidString));
        }        
        this.dateCreated = dateCreated;
        this.itemCount = this.list.size();
        this.listItemsT.addAll(this.list);
    }
    
    //full constructor
    /**
     * @deprecated
     */
    public UserListN(String name, ListType listType, ListSubType listSubType, List<String> list, List<String> invalidList, Date dateCreated, String notes)    {
        new UserListN(name, listType, listSubType, list, invalidList, dateCreated);
        this.notes = notes;        
    }
    
    //Base constructor plus listSubType
    /**
     * @deprecated
     */
    public UserListN(String name, ListType listType, ListSubType listSubType, List<String> list, List<String> invalidList, Date dateCreated)  {
        new UserListN(name,listType,list,invalidList,dateCreated);
        this.listSubType = listSubType;        
    }

    public Set<ListItem> getListItemsT(){
    	return this.listItemsT;
    }
    

    
    public void setListItemsT(Set<ListItem> listItemsT) {
 //   	List<ListItem> tempList = new ArrayList<ListItem>(listItemsT);
 //   	this.listItems = tempList;
       this.list.addAll(listItemsT);
    	this.listItemsT = listItemsT;
    }
    
    public void loadListItemsT(ArrayList<ListItem> listMembers){
    	this.listItemsT.addAll(listMembers);
    }

/* for list sharing functionalities */
    private Set<ShareList> listShares = new HashSet<ShareList>();
    public Set<ShareList> getListShares(){
    	return(this.listShares);
    }
    public void setListShares(Set<ShareList> listShares) {
    	    	this.listShares=listShares;
    }
    
    private List<GroupManager> groupsSaredWith = new ArrayList<GroupManager>();
    public List<GroupManager> getGroupsSharesWith(){
    	return(this.groupsSaredWith);
    }
    public void setGroupsSharesWith(List<GroupManager> groupsSaredWith) {
    	    	this.groupsSaredWith=groupsSaredWith;
    }
    
    private List<GroupManager> groupsNotSaredWith = new ArrayList<GroupManager>();
    public List<GroupManager> getGroupsNotSharesWith(){
    	return(this.groupsNotSaredWith);
    }
    public void setGroupsNotSharesWith(List<GroupManager> groupsNotSaredWith) {
    	    	this.groupsNotSaredWith=groupsNotSaredWith;
    }
    
/*   
    public Set<ListItem> getList(){
    	return this.list;
    }
    
    public void setList(Set<ListItem> list) {
        this.list=list;
    }   
*/    
    public char getShareStatus(){
        return (this.shareStatus);
    }

    public void setShareStatus(char shareStatus){
        this.shareStatus=shareStatus;
    }
    
    /**
     * @return Returns the author.
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author The author to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @return Returns the category.
     */
    public String getCategory() {
        return category;
    }
    /**
     * @param category The category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * @return Returns the institute.
     */
    public String getInstitute() {
        return institute;
    }
    /**
     * @param institute The institute to set.
     */
    public void setInstitute(String institute) {
        this.institute = institute;
    }
    
    /**
     * @return Returns the dateCreated.
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * @param dateCreated The dateCreated to set.
     * @throws ParseException 
     */
    public void setDateCreated(Date dateCreated) throws ParseException {
        this.dateCreated = dateCreated;
    }
    /**
     * @return Returns the itemCount.
     */
    public int getItemCount() {
        return list.size();
    }
    /**
     * @param itemCount The itemCount to set.
     */
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    /**
     * returns the list as ListItems
     * TODO (see setListItems) this wille eventually take the place of getList()
     * Currently it serves to return the old field (list) but will be phased in
     * on a per app basis.
     * 
     */

    public List<ListItem> getListItems(){
        return this.list;
    }
    
    /**
     * return the invalidList as ListItems
     * TODO SEE setListItems() same rules will apply. This is temporary
    */
    public List<ListItem> getInvalidListItems(){
        return this.invalidList;
    }
    
    /**
     * @deprecated
     * @return Returns the list (AS STRINGS).
     */

    public List<String> getList() {
        List<String> stringList = new ArrayList<String>();
        for(ListItem item: list){
            stringList.add(item.getName());
        }
        return stringList;
    }

    /**
     * @deprecated
     * @param list The list to set.
     */

    public void setList(List<String> list) {
        for(String s: list){
            this.list.add(new ListItem(s));
        } 
        
    }

    /**
     * @return Returns the listType.
     */
    public ListType getListType() {
        return listType;
    }
    /**
     * @param listType The listType to set.
     */
    public void setListType(ListType listType) {
        this.listType = listType;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    /**
     * @deprecated
     * @return Returns the invalidList (AS STRINGS).
     */
    public List<String> getInvalidList() {
        List<String> stringList = new ArrayList<String>();
        for(ListItem item: invalidList){
            stringList.add(item.getName());
        }
        return stringList;
    }
    /**
     * @deprecated
     * @param invalidList The invalidList to set.
     */
    public void setInvalidList(List<String> invalidList) {
        for(String s: invalidList){
            this.invalidList.add(new ListItem(s));
        } 
    }

    public ListSubType getListSubType() {
        return listSubType;
    }

    
    
    
    public void setListSubType(ListSubType listSubType) {
        this.listSubType = listSubType;
    }
    
    public boolean hasSubType(ListSubType listSubType)  {
        if(this.listSubType.equals(listSubType))  {
            return true;
        }
        return false;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId(){
        return (this.userId);
    }

    public void setUserId(Long userId){
        this.userId=userId;
    }

    /**
     * @param listItems The listItems to set.
     * TODO this needs to replace list field eventually. Right now it serves as
     * the 'proper way to set the listItems although most current apps are not using it.
     * The job right now for apps that do use it is to simply
     * assign it to the current field 'list'.
     */

    public void setListItems(List<ListItem> listItems) {
        this.list = listItems;
    }

    /**
     * @param invalidListItems The invalidListItems to set.
     * TODO see setListItems(). same rules apply, This is
     * temporary.
     */
    public void setInvalidListItems(List<ListItem> invalidListItems) {
        this.invalidList = invalidListItems;
    }

    /**
     * @return Returns the listOrigin.
     */
    public ListOrigin getListOrigin() {
        return listOrigin;
    }

    /**
     * @param listOrigin The listOrigin to set.
     */
    public void setListOrigin(ListOrigin listOrigin) {
        this.listOrigin = listOrigin;
    }

    
}
