package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.Artist;

public class ArtistController implements IDatabaseCrud<Artist> {
	
	@Override
	public void create(Artist entity) {
		try {
			Session session = databaseConnectionHibernate();
			session.getTransaction().begin();
			session.persist(entity);
			session.getTransaction().commit();
			System.out.println("ekleme tamamdır");
		} catch (Exception e) {
			
			System.out.println("ekleme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void delete(Artist entity) {
		try {
			Artist findEntity = find(entity.getId());
			if (findEntity != null) {
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.remove(findEntity);
				session.getTransaction().commit();
				System.out.println("silme tamamdır");
			}
		} catch (Exception e) {
			System.out.println("silme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Artist entity) {
		try {
			Artist findEntity = find(entity.getId());
			if (findEntity != null) {
				findEntity.setArtistName(entity.getArtistName());
				findEntity.setArtistSurname(entity.getArtistSurname());
				findEntity.setArtistInfo(entity.getArtistInfo());
				
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.merge(findEntity);
				session.getTransaction().commit();
				System.out.println("güncelleme tamamdır");
			}
			
		} catch (Exception e) {
			System.out.println("güncelleme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<Artist> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from Artist as str where str.id>=:key";
		TypedQuery<Artist> typedQuery = session.createQuery(hql, Artist.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<Artist> arrayList = (ArrayList<Artist>) typedQuery.getResultList();
		System.out.println("listelendi " + Artist.class);
		return arrayList;
	}
	
	@Override
	public Artist find(long id) {
		Session session = databaseConnectionHibernate();
		Artist artistEntity;
		try {
			artistEntity = session.find(Artist.class, id);
			
			if (artistEntity != null) {
				System.out.println("bulundu... " + artistEntity);
				return artistEntity;
			} else {
				System.out.println("Aradığınız kriterde sonuçlar bulunamadı ...");
				return null;
			}
		} catch (Exception e) {
			System.out.println("find anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		return null;
	}
	
}
