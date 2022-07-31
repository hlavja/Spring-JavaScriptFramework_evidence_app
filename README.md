Tasks:
-- 
- Add attributes “version“, “deprecationDate” a “hypeLevel” to the Framework entity. [JavaScriptFramework.java](src/main/java/com/etnetera/hr/data/JavaScriptFramework.java) -- DONE
- Creates new Frameworks -> POST /framework (JSON body). [JavaScriptFrameworkController.java](src/main/java/com/etnetera/hr/controller/JavaScriptFrameworkController.java) -- DONE
- Edit framework entity -> PUT /framework (JSON body). [JavaScriptFrameworkController.java](src/main/java/com/etnetera/hr/controller/JavaScriptFrameworkController.java) -- DONE
- Delete framework entity -> DELETE /framework/{id} (ID in path variable). [JavaScriptFrameworkController.java](src/main/java/com/etnetera/hr/controller/JavaScriptFrameworkController.java) -- DONE
- Search framework -> GET /framework?id=&name=&version=&hypeLevel=&deprecationDateGt=&deprecationDateLt= (Query params). [JavaScriptFrameworkController.java](src/main/java/com/etnetera/hr/controller/JavaScriptFrameworkController.java) -- DONE
  - id - framework id
  - name - framework name
  - version - framework version
  - hypeLevel - framework hype level
  - deprecationDateGt - frameworks with deprecation date greater than
  - deprecationDateLt - frameworks with deprecation date lower than
- Write tests. [JavaScriptFrameworkTests.java](src/test/java/com/etnetera/hr/JavaScriptFrameworkTests.java) -- DONE see @
