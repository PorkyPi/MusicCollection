-- drop table folder;

 CREATE TABLE folder(
	 id INTEGER PRIMARY KEY,
	 folder_name VARCHAR(60) not NULL,
	 purpose_id INTEGER not NULL,
	 parent_folder INTEGER,
	 path_to_image VARCHAR(250),
   FOREIGN KEY(parent_folder) REFERENCES folder(id),
   FOREIGN KEY(purpose_id) REFERENCES catalog_folder(id)
 );