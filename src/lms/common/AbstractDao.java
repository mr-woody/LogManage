package lms.common;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import lms.struts.tags.Page;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractDao extends HibernateDaoSupport {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	public abstract Class<?> getReferenceClass();
	
	@Autowired
	public void setSessionFactoryNew(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	//[start] 添加方法
	/**
	 * 添加一个实体对象
	 * @param object 待添加实体
	 * @return	添加结果  <br/>1：成功 <br/> -1：失败
	 */
	public int addObject(Object object) {
		try {
			super.getHibernateTemplate().save(object);
			return 1;
		} catch (DataAccessException e) {
			logger.error("删除失败", e);
			return -1;
		}
	}

	/**
	 * 添加多个实体
	 * @param objects 待添加实体集合
	 * @return    添加结果  <br/>1：成功 <br/> -1：失败
	 */
	public <T> int addObjects(Collection<T> objects) {
		try {
			for (T item : objects) {
				super.getHibernateTemplate().save(item);
			}
			return objects.size();
		} catch (DataAccessException e) {
			logger.error("删除失败", e);
			return -1;
		}
	}
	//[end]
	
	//[start] 删除方法
	public int deleteByPks(Serializable... pk) {
		try {
			for (Serializable s : pk) {
				Object obj = getObjectByPK(s);
				getHibernateTemplate().delete(obj);
			}
			logger.debug("删除成功");
			return 1;
		} catch (RuntimeException re) {
			logger.error("删除失败", re);
			return -1;
		}
	}
	/**
	 * 删除一个实体对象
	 * @param object 待删除对象
	 * @return 删除结果<br/> 1：成功<br/> 0：失败
	 */
	public int deleteObj(Object object) {
		try {
			super.getHibernateTemplate().delete(object);
			return 1;
		} catch (DataAccessException e) {
			logger.error("删除失败", e);
			return -1;
		}
	}

	/**
	 * 删除多个实体对象
	 * @param objects 待删除对象集合
	 * @return 删除结果<br/> 1：成功<br/> 0：失败
	 */
	public <T> int deleteObjs(Collection<T> objects) {
		try {
			for (T item : objects) {
				super.getHibernateTemplate().delete(item);
			}
			return 1;
		} catch (DataAccessException e) {
			logger.error("删除失败", e);
			return -1;
		}
	}
	//[end]

	//[start] 更新方法
	/**
	 * 更新一个实体对象
	 * @param object 待更新实体对象
	 * @return 更新结果 <br/>1：成功 <br/>-1：失败
	 */
	public int updateObj(Object object,boolean isClear) {
		try {
			if(isClear)super.getSessionFactory().getCurrentSession().clear();
			super.getHibernateTemplate().update(object);
			return 1;
		} catch (DataAccessException e) {
			logger.error("更新失败", e);
			return -1;
		}
	}
	
	/**
	 * 更新多个实体对象
	 * @param object 待更新实体对象集合
	 * @return 更新结果<br/>1：成功 <br/>-1：失败
	 */
	public <T> int updateObjs(Collection<T> objects,boolean isClear) {
		try {
			if(isClear)super.getSessionFactory().getCurrentSession().clear();
			for (T item : objects) {
				super.getHibernateTemplate().update(item);
			}
			return 1 ;
		} catch (DataAccessException e) {
			logger.error("更新失败", e);
			return -1;
		}
	}
	//[end]
	
	//[start] 查询方法(单个)
	/**
	 * 通过对象主键得到对象实例
	 * 
	 * @param pk
	 * @return
	 */
	public Object getObjectByPK(Serializable pk) {
		try {
			return getHibernateTemplate().get(getReferenceClass(), pk);
		} catch (RuntimeException re) {
			logger.error("通过对象主键得到对象实例,操作失败:" + pk, re);
			re.printStackTrace();
		}
		return null;
	}

	public Object getObjectByPk(Class<? extends Object> className,
			Serializable pk) {
		try {
			return getHibernateTemplate().get(className, pk);
		} catch (RuntimeException re) {
			logger.error("通过对象主键得到对象实例,操作失败:" + pk, re);
			re.printStackTrace();
		}
		return null;
	}
	//[end]
	
	//[start] 查询方法
	/**
	 * 查询所有对象
	 * @param tClass 待查询实体Class
	 * @return 实体对象集合
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> queryAll(){
		List<T> loadAll = null;
		try {
			loadAll = (List<T>)super.getHibernateTemplate().loadAll(getReferenceClass());
		} catch (Exception e) {
			logger.error("查询失败",e);
		}
		return loadAll;
					
	}
	
	/**
	 * 根据条件查询实体对象集合
	 * @param conditions 查询条件
	 * @param params 参数集合
	 * @return 实体对象集合
	 */
	@SuppressWarnings({ "unchecked"})
	public <T> Collection<T> queryByConditions(String condition,Object[] params) {
		try {
			return super.getHibernateTemplate().find(condition,params);
		} catch (DataAccessException e) {
			logger.error("按条件查询数据失败:" + condition,e);
		}
		return null;
	}
	/**
	 * 根据条件查询实体对象集合
	 * @param conditions 查询条件
	 * @return 实体对象集合
	 */
	@SuppressWarnings({ "unchecked"})
	public <T> Collection<T> queryByConditions(String condition) {
		try {
			return super.getHibernateTemplate().find(condition);
		} catch (DataAccessException e) {
			logger.error("按条件查询数据失败:" + condition,e);
		}
		return null;
	}
	//[end]
	
	//[start] 查询方法(分页)
	
	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return 查询结果
	 */
	public <T> Collection<T> queryAllByPage(Page page) {
		try {
			return queryByConditions(" from " + getReferenceClass().getName(),page);
		} catch (RuntimeException re) {
			logger.error("得到全部数据并运行分页失败", re);
			re.printStackTrace();
		}
		return null;
	}

	/**
	 * 按条件进行分页查询
	 * @param conditions 查询条件
	 * @param page 分页对象
	 * @return 查询结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public <T> Collection<T> queryByConditions(final String conditions,final Page page) {
		try {
			int currentPage = page.getCurrentPage();
			int everyPage = Page.getEveryPage();
			page.setElementTotal(getObjectTotalByConditions(conditions));
			int maxSize = page.getElementTotal();
			page.setTotalPage(maxSize / everyPage + 1);
			Collection<T> queryResult =  super.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					int currentPage = page.getCurrentPage();
					int everyPage =  Page.getEveryPage();
					Query query = session.createQuery(conditions);
					return query.setFirstResult((currentPage - 1)*everyPage).setMaxResults(everyPage).list();
				}
			});
			return queryResult;
		} catch (RuntimeException re) {
			logger.error("按条件查询数据并运行分页失败:" + conditions);
			re.printStackTrace();
		}
		return null;
	}
	//[end]
	
	//[start] 常用方法
	/**
	 * 查询指定实体总数
	 * @param tClass 待查询实体Class
	 * @return 实体总数
	 */
	public <T> int objectCount() {
		try {
			return getHibernateTemplate().loadAll(getReferenceClass()).size();
		} catch (DataAccessException e) {
			logger.error("查询对象总条数失败",e);
		}
		return 0;
	}
	
	/**
	 * 根据条件查询实体总数
	 * @param condition 查询条件
	 * @return 实体总数
	 */
	public int objectCountByConditionsOfSQL(String condition) {
		try {
			return queryBySQL(condition).size();
		} catch (RuntimeException re) {
			logger.error("按条件查询对象总条数失败(使用SQL语句)");
		}
		return 0;
	}
	
	/**
	 * 根据sql语句查询实体集合
	 * @param sql sql语句
	 * @return 实体集合
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> queryBySQL(String sql) {
		try {
			return getSession().createSQLQuery(sql).list();
		} catch (HibernateException e) {
			logger.error("使用SQL语句查询失败",e);
		} finally {
			releaseSession(getSession());
		}
		return null;
	}
	
	/**
	 * 根据hql语句查询实体集合
	 * @param hql hql语句
	 * @return 实体集合
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> queryByHQL(String hql) {
		try {
			return getSession().createQuery(hql).list();
		} catch (HibernateException e) {
			logger.error("使用HQL语句查询失败",e);
		} finally {
			releaseSession(getSession());
		}
		return null;
	}
	public int getObjectTotalByConditions(String conditions) {
		try {
			return getHibernateTemplate().find(conditions).size();
		} catch (RuntimeException re) {
			logger.error("按条件查询对象总条数失败");
			System.err.println(re);
		}
		return 0;
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 */
	public void executeUpdate(final String sql){
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){  
            public Object doInHibernate(final Session session){ 
				return session.createQuery(sql).executeUpdate();
            }
		});
	}
	//[end]
}
