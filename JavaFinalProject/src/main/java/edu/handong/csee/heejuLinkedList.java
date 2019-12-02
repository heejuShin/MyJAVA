package edu.handong.csee;


public class heejuLinkedList<T> {
	private ListNode head;
	private ListNode current;
	private ListNode previous;
	
	public heejuLinkedList() {
		head = null;
		current = null;
		previous = null;
	}

	public void showList() {
		ListNode position = head;
		while(position != null) {
			System.out.println("-->"+position.data);
			position = position.link;
		}
	}
	
	public String getList(int num) {
		ListNode position = head;
		//lists = null;
		int count=0;
		String list=null;
		while(position != null) {
			//System.out.println(position.data);
			if(count==num)
				list=position.data;
			position = position.link;
			count++;
		};
		return list;
	}
	
	public int length() {
		int count = 0;
		ListNode position = head;
		while(position != null) {
			count++;
			position = position.link;
		}
		return count;
	}
	
	public void addANodeToStart(String addData) {
		head = new ListNode(addData, head);
		if((current == head.link) && (current != null)) {
			previous = head;
		}
	}
	
	public void resetIteration() {
		current = head;
		previous = null;
	}
	
	public boolean moreToIterate() {
		return current != null;
	}
	
	public void goToNext() {
		if(current != null) {
			previous = current;
			current = current.link;
		}else if(head!=null) {
			System.out.println("Iterated too many times or uninitialized iteration.");
			System.exit(0);
		}else {
			System.out.println("Iterating with an empty list.");
			System.exit(0);
		}
	}
	
	public String getDataAtCurrent() {
		String result = null;
		if(current != null) {
			result = current.data;
		}else {
			System.out.println("Getting data when current is not at any node");
			System.exit(0);
		}
		return result;
	}
	
	public void setDataAtCurrent(String newData) {
		if(current != null) {
			current.data = newData;
		}else {
			System.out.println("Setting data when current is not any node");
			System.exit(0);
		}
	}
	
	public void insertNodeAfterCurrent(String newData) {
		ListNode newNode = new ListNode();
		newNode.data = newData;
		if(current != null) {
			newNode.link = current.link;
			current.link = newNode;
		}else if(head != null) {
			System.out.println("Inserting when iterator is past all nodes or is not initialzed");
			System.exit(0);
		}else {
			System.out.println("Using insertNodeAfterCurrent with empty list.");
			System.exit(0);
		}
	}
	
	public void deleteCurrentNode() {
		if((current != null) && (previous == null)) {
			previous.link = current.link;
			current = current.link;
		}else if((current != null) && (previous == null)) {
			head = current.link;
			current = head;
		}else {
			System.out.println("Deleting with uninitialized current or an empty list.");
			System.exit(0);
		}
	}
	
	public void deleteHeadNode() {
		if(head != null)
			head = head.link;
		else {
			System.out.println("Deleting from an empty list.");
			System.exit(0);
		}
	}
	
	public boolean onList(String target) {
		return find(target) != null;
	}
	
	private ListNode find(String target) {
		boolean found = false;
		ListNode position = head;
		while((position != null) && !found) {
			String dataAtPosition = position.data;
			if(dataAtPosition.equals(target))
				found = true;
			else
				position = position.link;
		}
		return position;
	}
	
	public String[] toArray() {
		String[] anArray = new String[length()];
		ListNode position = head;
		int i= 0;
		while(position != null) {
			anArray[i] = position.data;
			i++;
			position = position.link;
		}
		return anArray;
	}
	
	private class ListNode{
		private String data;
		private ListNode link;
		
		public ListNode() {
			link = null;
			data = null;
		}
		
		public ListNode(String newData, ListNode linkedNode) {
			data = newData;
			link = linkedNode;
		}
	}
	
}